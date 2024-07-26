package my.project.vehiclefleetmanagement.service.car;

import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonEditDTO;

import java.util.List;

public interface DriverService {

    boolean createDriver(CarPersonCreateDTO carPersonCreateDTO);

    List<CarPersonDTO> getAllDriver(Long id);

    CarPersonDTO getDriverById(Long id);

    void deleteDriver(Long id);

    boolean updateDriver(Long id, CarPersonEditDTO carPersonEditDTO);
}
