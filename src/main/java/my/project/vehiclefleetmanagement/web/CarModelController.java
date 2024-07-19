package my.project.vehiclefleetmanagement.web;

import my.project.vehiclefleetmanagement.model.dtos.carModel.CarModelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.carModel.CarModelDTO;
import my.project.vehiclefleetmanagement.model.dtos.carModel.CarModelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.carModel.CarModelListDTO;
import my.project.vehiclefleetmanagement.service.CarModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carModel")

public class CarModelController {

    private final CarModelService carModelService;

    public CarModelController(CarModelService carModelService) {
        this.carModelService = carModelService;
    }

    @GetMapping
    public ResponseEntity<List<CarModelListDTO>> getAllModel() {
        return ResponseEntity.ok(
                carModelService.getAllModels()
        );
    }

    @PostMapping
    public ResponseEntity<String> createCarModel(@RequestBody CarModelCreateDTO carModelCreateDTO) {

        boolean success = carModelService.createModel(carModelCreateDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Model already exist!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCarModel(@PathVariable("id") Long id,@RequestBody CarModelEditDTO carModelEditDTO) {

        boolean success = carModelService.updateModel(id,carModelEditDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Model not found!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarModelDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(carModelService.getModelById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        carModelService.deleteModel(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
