package my.project.vehiclefleetmanagement.service;


import my.project.vehiclefleetmanagement.model.dtos.user.*;

import java.util.List;

public interface UserService {

//  boolean registerUser(UserRegistrationDTO userRegistration);

  UserDto login(CredentialsDto credentialsDto);

  UserDto register(SignUpDto userDto);

  UserDto findByUsername(String username);

  List<UserListDTO> getAllUsers();

  void updateUser(Long id, UserEditDTO userEditDTO);

  UserByIdDto getUserById(Long id);

  void deleteUser(Long id);

  List<UserRoleDto> getAllRoles();

}