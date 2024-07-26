package my.project.vehiclefleetmanagement.web.nomenclatures;

import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelListDTO;
import my.project.vehiclefleetmanagement.service.nomenclatures.CarModelService;
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
        return ResponseEntity.ok(carModelService.getAllModels());
    }

    @PostMapping
    public ResponseEntity<String> createCarModel(@RequestBody CarModelCreateDTO carModelCreateDTO) {
        carModelService.createModel(carModelCreateDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCarModel(@PathVariable("id") Long id, @RequestBody CarModelEditDTO carModelEditDTO) {
        carModelService.updateModel(id, carModelEditDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarModelDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(carModelService.getModelById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        carModelService.deleteModel(id);
        return ResponseEntity.noContent().build();
    }
}
