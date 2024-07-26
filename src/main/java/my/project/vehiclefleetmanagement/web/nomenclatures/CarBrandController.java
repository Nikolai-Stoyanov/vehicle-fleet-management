package my.project.vehiclefleetmanagement.web.nomenclatures;

import jakarta.validation.Valid;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandListDTO;
import my.project.vehiclefleetmanagement.service.nomenclatures.CarBrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carBrand")
@CrossOrigin
public class CarBrandController {

    private final CarBrandService carBrandService;

    public CarBrandController(CarBrandService carBrandService) {
        this.carBrandService = carBrandService;
    }

    @GetMapping
    public ResponseEntity<List<CarBrandListDTO>> getAllBrands() {
        return ResponseEntity.ok(carBrandService.getAllBrands());
    }

    @PostMapping
    public ResponseEntity<String> createCarBrand(@RequestBody @Valid CarBrandCreateDTO carBrandCreateDTO) {
        carBrandService.createBrand(carBrandCreateDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCarBrand(@PathVariable("id") Long id, @RequestBody @Valid CarBrandEditDTO carBrandEditDTO) {
        carBrandService.updateBrand(id, carBrandEditDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarBrandDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(carBrandService.getBrandById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        carBrandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
