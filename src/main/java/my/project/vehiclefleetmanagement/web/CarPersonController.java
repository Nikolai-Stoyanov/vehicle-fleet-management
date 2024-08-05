package my.project.vehiclefleetmanagement.web;

import jakarta.validation.Valid;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonEditDTO;
import my.project.vehiclefleetmanagement.service.CarPersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carPerson")

public class CarPersonController {

    private final CarPersonService carPersonService;

    public CarPersonController(CarPersonService carPersonService) {
        this.carPersonService = carPersonService;
    }

    @GetMapping
    public ResponseEntity<List<CarPersonDTO>> getAllCarPerson() {
        return ResponseEntity.ok(
                this.carPersonService.getAllCarPerson()
        );
    }

    @PostMapping
    public ResponseEntity<String> createCarPerson(@RequestBody @Valid CarPersonCreateDTO carPersonCreateDTO) {
        this.carPersonService.createCarPerson(carPersonCreateDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCarPerson(@PathVariable("id") Long id, @RequestBody @Valid CarPersonEditDTO carPersonEditDTO) {
        this.carPersonService.updateCarPerson(id, carPersonEditDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarPersonDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(this.carPersonService.getCarPersonById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        this.carPersonService.deleteCarPerson(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
