package my.project.vehiclefleetmanagement.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import my.project.vehiclefleetmanagement.config.UserMapper;
import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.user.UserDto;
import my.project.vehiclefleetmanagement.model.dtos.user.UserRoleDto;
import my.project.vehiclefleetmanagement.model.entity.user.UserRole;
import my.project.vehiclefleetmanagement.model.enums.UserRoleEnum;
import my.project.vehiclefleetmanagement.repository.UserRepository;
import my.project.vehiclefleetmanagement.repository.UserRolesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    private final UserMapper userMapper;
    private final ModelMapper modelMapper;
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 10800000); // 3 hour
        List<String> roles = new ArrayList<>();
        for (UserRoleDto role : user.getRoles()) {
            roles.add(role.getRole().toString());
        }
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("email", user.getEmail())
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);
        List<UserRoleDto> roleDtos=new ArrayList<>();
        for (String role : decoded.getClaim("roles").asList(String.class)){
            UserRole userRole = this.userRolesRepository.findByRole(UserRoleEnum.valueOf(role));
            roleDtos.add(modelMapper.map(userRole, UserRoleDto.class));
        }
        UserDto user = UserDto.builder()
                .username(decoded.getSubject())
                .email(decoded.getClaim("email").asString())
                .roles(roleDtos)
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = userMapper.toUserDto(userRepository.findByUsername(decoded.getSubject())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND)));

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

}
