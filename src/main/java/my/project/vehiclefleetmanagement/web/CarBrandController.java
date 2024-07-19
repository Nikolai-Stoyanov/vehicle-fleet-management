package my.project.vehiclefleetmanagement.web;

import my.project.vehiclefleetmanagement.model.dtos.carBrand.CarBrandCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.carBrand.CarBrandDTO;
import my.project.vehiclefleetmanagement.model.dtos.carBrand.CarBrandEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.carBrand.CarBrandListDTO;
import my.project.vehiclefleetmanagement.service.CarBrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carBrand")

public class CarBrandController {

    private final CarBrandService carBrandService;

    public CarBrandController( CarBrandService carBrandService) {
        this.carBrandService = carBrandService;
    }

    @GetMapping
    public ResponseEntity<List<CarBrandListDTO>> getAllBrands() {
        return ResponseEntity.ok(
                carBrandService.getAllBrands()
        );
    }

    @PostMapping
    public ResponseEntity<String> createCarBrand(@RequestBody CarBrandCreateDTO carBrandCreateDTO) {

        boolean success = carBrandService.createBrand(carBrandCreateDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Brand already exist!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCarBrand(@PathVariable("id") Long id,@RequestBody CarBrandEditDTO carBrandEditDTO) {

        boolean success = carBrandService.updateBrand(id,carBrandEditDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand not found!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarBrandDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(carBrandService.getBrandById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        carBrandService.deleteBrand(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
