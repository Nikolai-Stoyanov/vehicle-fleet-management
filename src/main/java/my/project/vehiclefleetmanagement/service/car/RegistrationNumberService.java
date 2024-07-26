package my.project.vehiclefleetmanagement.service.car;


import my.project.vehiclefleetmanagement.model.dtos.car.registrationNumber.RegistrationNumberCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.registrationNumber.RegistrationNumberDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.registrationNumber.RegistrationNumberEditDTO;

import java.util.List;

public interface RegistrationNumberService {

    boolean createRegistrationNumber(RegistrationNumberCreateDTO registrationNumberCreateDTO);

    List<RegistrationNumberDTO> getAllRegistrationNumber(Long id);

    RegistrationNumberDTO getRegistrationNumberById(Long id);

    void deleteRegistrationNumber(Long id);

    boolean updateRegistrationNumber(Long id, RegistrationNumberEditDTO registrationNumberEditDTO);
}
