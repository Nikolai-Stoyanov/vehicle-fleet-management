package my.project.vehiclefleetmanagement.service;

import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonEditDTO;

import java.util.List;

public interface CarPersonService {

    void createCarPerson(CarPersonCreateDTO carPersonCreateDTO);

    List<CarPersonDTO> getAllCarPerson();

    CarPersonDTO getCarPersonById(Long id);

    void deleteCarPerson(Long id);

    void updateCarPerson(Long id, CarPersonEditDTO carPersonEditDTO);
}
