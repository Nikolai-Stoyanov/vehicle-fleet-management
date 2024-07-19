package my.project.vehiclefleetmanagement.service;


import my.project.vehiclefleetmanagement.model.dtos.user.UserRegistrationDTO;

public interface UserService {

  boolean registerUser(UserRegistrationDTO userRegistration);

}
