package my.project.vehiclefleetmanagement.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.project.vehiclefleetmanagement.security.UserAuthenticationProvider;
import my.project.vehiclefleetmanagement.model.dtos.user.CredentialsDto;
import my.project.vehiclefleetmanagement.model.dtos.user.SignUpDto;
import my.project.vehiclefleetmanagement.model.dtos.user.UserDto;
import my.project.vehiclefleetmanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        userService.register(user);
        return ResponseEntity.noContent().build();
    }
}
