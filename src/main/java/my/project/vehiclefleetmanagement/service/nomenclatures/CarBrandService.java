package my.project.vehiclefleetmanagement.service.nomenclatures;

import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandListDTO;

import java.util.List;

public interface CarBrandService {

    boolean createBrand(CarBrandCreateDTO carBrandCreateDTO);

    List<CarBrandListDTO> getAllBrands();

    CarBrandDTO getBrandById(Long id);

    void deleteBrand(Long id);

    boolean updateBrand(Long id, CarBrandEditDTO carBrandEditDTO);
}