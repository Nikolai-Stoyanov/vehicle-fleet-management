package my.project.vehiclefleetmanagement.service;

import my.project.vehiclefleetmanagement.model.dtos.carBrand.CarBrandCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.carBrand.CarBrandDTO;
import my.project.vehiclefleetmanagement.model.dtos.carBrand.CarBrandEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.carBrand.CarBrandListDTO;

import java.util.List;

public interface CarBrandService {

    boolean createBrand(CarBrandCreateDTO carBrandCreateDTO);

    List<CarBrandListDTO> getAllBrands();

    CarBrandDTO getBrandById(Long id);

    void deleteBrand(Long id);

    boolean updateBrand(Long id, CarBrandEditDTO carBrandEditDTO);
}
