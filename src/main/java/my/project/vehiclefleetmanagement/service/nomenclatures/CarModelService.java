package my.project.vehiclefleetmanagement.service.nomenclatures;

import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelListDTO;

import java.util.List;

public interface CarModelService {
    List<CarModelListDTO> getAllModels();

    void createModel(CarModelCreateDTO carModelCreateDTO);

    void updateModel(Long id, CarModelEditDTO carModelEditDTO);

    CarModelDTO getModelById(Long id);

    void deleteModel(Long id);
}
