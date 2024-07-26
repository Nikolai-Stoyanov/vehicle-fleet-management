package my.project.vehiclefleetmanagement.service.car;

import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonEditDTO;

import java.util.List;

public interface ResponsibleService {

    boolean createResponsible(CarPersonCreateDTO carPersonCreateDTO);

    List<CarPersonDTO> getAllResponsible(Long id);

    CarPersonDTO getResponsibleById(Long id);

    void deleteResponsible(Long id);

    boolean updateResponsible(Long id, CarPersonEditDTO carPersonEditDTO);
}
