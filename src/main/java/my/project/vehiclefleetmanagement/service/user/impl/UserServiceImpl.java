package my.project.vehiclefleetmanagement.service.user.impl;

import my.project.vehiclefleetmanagement.config.UserMapper;
import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.user.*;
import my.project.vehiclefleetmanagement.model.entity.user.UserEntity;
import my.project.vehiclefleetmanagement.model.entity.user.UserRole;
import my.project.vehiclefleetmanagement.repository.UserRepository;
import my.project.vehiclefleetmanagement.repository.UserRolesRepository;
import my.project.vehiclefleetmanagement.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    private final UserMapper userMapper;
    private final ModelMapper modelMapper;

    public UserServiceImpl(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            UserRolesRepository userRolesRepository,
            UserMapper userMapper,
            ModelMapper modelMapper
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
        this.userMapper = userMapper;
        this.modelMapper = modelMapper;
    }


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
        Optional<UserRole> userRole;
        if (!userRepository.findAll().isEmpty()){
             userRole=this.userRolesRepository.findById(2L);
        }else {
             userRole=this.userRolesRepository.findById(1L);
        }

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

    @Override
    public List<UserListDTO> getAllUsers() {
        List<UserEntity> userEntityList=  userRepository.findAll();
        List<UserListDTO> userListDTOS = new ArrayList<>();
        for (UserEntity user : userEntityList) {
            UserListDTO userListDTO = modelMapper.map(user, UserListDTO.class);

            List<String> userRolesDTO = new ArrayList<>();
            for (UserRole roleDto : user.getRoles()) {
                userRolesDTO.add(roleDto.getRole().toString());
            }
            userListDTO.setRoles(userRolesDTO);

            userListDTOS.add(userListDTO);
        }
        return userListDTOS;
    }

    @Override
    public void updateUser(Long id, UserEditDTO userEditDTO) {
        Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);
        if (userEntityOptional.isEmpty()) {
            throw new AppException("User is not found!", HttpStatus.NOT_FOUND);
        }
        userEntityOptional.get().setUsername(userEditDTO.getUsername());
        userEntityOptional.get().setEmail(userEditDTO.getEmail());
        List<UserRole> userRoles = new ArrayList<>();
        for (UserRoleDto roleDto : userEditDTO.getRoles()) {
            userRoles.add(modelMapper.map(roleDto, UserRole.class));
        }
        userEntityOptional.get().setRoles(userRoles);
        this.userRepository.save(userEntityOptional.get());
        throw new AppException("User successfully updated!", HttpStatus.OK);
    }

    @Override
    public UserByIdDto getUserById(Long id) {
        Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);
        return userEntityOptional.map(user -> modelMapper.map(user, UserByIdDto.class)).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);
        if (userEntityOptional.isEmpty()) {
            throw new AppException("User is not found!", HttpStatus.NOT_FOUND);
        }
        this.userRepository.deleteById(id);
        throw new AppException("User successfully deleted!", HttpStatus.OK);
    }

    @Override
    public List<UserRoleDto> getAllRoles() {
        List<UserRole> userRoles=  userRolesRepository.findAll();
        List<UserRoleDto> userRoleDtos = new ArrayList<>();
        for (UserRole role : userRoles) {
            userRoleDtos.add(modelMapper.map(role, UserRoleDto.class));
        }
        return userRoleDtos;
    }

}
