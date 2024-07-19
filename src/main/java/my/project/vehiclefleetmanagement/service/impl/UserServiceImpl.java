package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.model.dtos.user.UserRegistrationDTO;
import my.project.vehiclefleetmanagement.model.entity.UserEntity;
import my.project.vehiclefleetmanagement.repository.UserRepository;
import my.project.vehiclefleetmanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean registerUser(UserRegistrationDTO userRegistration) {
        boolean isUserNameOrEmailExist = this.userRepository.existsByUsernameOrEmail(userRegistration.getUsername(), userRegistration.getEmail());
        boolean isPasswordsSame=userRegistration.getPassword().equals(userRegistration.getConfirmPassword());
        if (isUserNameOrEmailExist || !isPasswordsSame) {
            return false;
        }
        UserEntity mappedEntity = modelMapper.map(userRegistration, UserEntity.class);

        mappedEntity.setPassword(passwordEncoder.encode(userRegistration.getPassword()));

        this.userRepository.save(mappedEntity);
        return true;
    }
}
