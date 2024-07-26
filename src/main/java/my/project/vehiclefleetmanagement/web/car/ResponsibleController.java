package my.project.vehiclefleetmanagement.web.car;

import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonEditDTO;
import my.project.vehiclefleetmanagement.service.car.ResponsibleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsible")

public class ResponsibleController {

    private final ResponsibleService responsibleService;

    public ResponsibleController(ResponsibleService responsibleService) {
        this.responsibleService = responsibleService;
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<List<CarPersonDTO>> getAllDriver(@PathVariable("ownerId") Long id) {
        return ResponseEntity.ok(
                this.responsibleService.getAllResponsible(id)
        );
    }

    @PostMapping
    public ResponseEntity<String> createDriver(@RequestBody CarPersonCreateDTO carPersonCreateDTO) {

        boolean success = this.responsibleService.createResponsible(carPersonCreateDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Responsible already exist!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDriver(@PathVariable("id") Long id,@RequestBody CarPersonEditDTO carPersonEditDTO) {

        boolean success = this.responsibleService.updateResponsible(id, carPersonEditDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Responsible not found!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarPersonDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(this.responsibleService.getResponsibleById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        this.responsibleService.deleteResponsible(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
