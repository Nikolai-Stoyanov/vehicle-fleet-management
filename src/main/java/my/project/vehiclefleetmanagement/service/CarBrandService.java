package my.project.vehiclefleetmanagement.service;

import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandListDTO;

import java.util.List;

public interface CarBrandService {

    void createBrand(CarBrandCreateDTO carBrandCreateDTO);

    List<CarBrandListDTO> getAllBrands();

    CarBrandDTO getBrandById(Long id);

    void deleteBrand(Long id);

    void updateBrand(Long id, CarBrandEditDTO carBrandEditDTO);
}
