package my.project.vehiclefleetmanagement.service.user.impl;

import my.project.vehiclefleetmanagement.config.UserMapper;
import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.user.CredentialsDto;
import my.project.vehiclefleetmanagement.model.dtos.user.SignUpDto;
import my.project.vehiclefleetmanagement.model.dtos.user.UserDto;
import my.project.vehiclefleetmanagement.model.entity.user.UserEntity;
import my.project.vehiclefleetmanagement.model.entity.user.UserRole;
import my.project.vehiclefleetmanagement.repository.UserRepository;
import my.project.vehiclefleetmanagement.repository.UserRolesRepository;
import my.project.vehiclefleetmanagement.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, UserRolesRepository userRolesRepository, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
        this.userMapper = userMapper;
    }
//
//    @Override
//    public boolean registerUser(UserRegistrationDTO userRegistration) {
//        boolean isUserNameOrEmailExist = this.userRepository1.existsByUsernameOrEmail(userRegistration.getUsername(), userRegistration.getEmail());
//        boolean isPasswordsSame=userRegistration.getPassword().equals(userRegistration.getConfirmPassword());
//        if (isUserNameOrEmailExist || !isPasswordsSame) {
//            return false;
//        }
//        UserEntity mappedEntity = modelMapper.map(userRegistration, UserEntity.class);
//
//        mappedEntity.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
//        Optional<UserRole> userRole=this.userRolesRepository.findById(2L);
//        mappedEntity.setRoles(List.of(userRole.get()));
//        this.userRepository1.save(mappedEntity);
//        return true;
//    }

    @Override
    public UserDto login(CredentialsDto credentialsDto) {
        UserEntity user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(credentialsDto.password(), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDto register(SignUpDto signUpDto) {
        Optional<UserEntity> optionalUser = userRepository.findByUsernameOrEmail(signUpDto.getUsername(),signUpDto.getEmail());
        boolean isPasswordsSame=signUpDto.getPassword().equals(signUpDto.getConfirmPassword());
        if (optionalUser.isPresent()) {
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }
        if (!isPasswordsSame) {
            throw new AppException("Password and Confirm Password Field do not match", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userMapper.signUpToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Optional<UserRole> userRole=this.userRolesRepository.findById(2L);
        user.setRoles(List.of(userRole.get()));

        UserEntity savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    @Override
    public UserDto findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }
}
