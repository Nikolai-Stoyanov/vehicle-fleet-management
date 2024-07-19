package my.project.vehiclefleetmanagement.service;

import my.project.vehiclefleetmanagement.model.dtos.carModel.CarModelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.carModel.CarModelDTO;
import my.project.vehiclefleetmanagement.model.dtos.carModel.CarModelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.carModel.CarModelListDTO;

import java.util.List;

public interface CarModelService {
    List<CarModelListDTO> getAllModels();

    boolean createModel(CarModelCreateDTO carModelCreateDTO);

    boolean updateModel(Long id, CarModelEditDTO carModelEditDTO);

    CarModelDTO getModelById(Long id);

    void deleteModel(Long id);
}
