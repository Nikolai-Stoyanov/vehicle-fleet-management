package my.project.vehiclefleetmanagement.service.user;


import my.project.vehiclefleetmanagement.model.dtos.user.CredentialsDto;
import my.project.vehiclefleetmanagement.model.dtos.user.SignUpDto;
import my.project.vehiclefleetmanagement.model.dtos.user.UserDto;

public interface UserService {

//  boolean registerUser(UserRegistrationDTO userRegistration);

  UserDto login(CredentialsDto credentialsDto);

  UserDto register(SignUpDto userDto);

  UserDto findByUsername(String username);

}
