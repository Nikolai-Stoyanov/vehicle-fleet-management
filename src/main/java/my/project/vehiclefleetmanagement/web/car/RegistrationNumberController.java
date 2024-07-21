package my.project.vehiclefleetmanagement.web.car;

import my.project.vehiclefleetmanagement.model.dtos.car.registrationNumber.RegistrationNumberCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.registrationNumber.RegistrationNumberDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.registrationNumber.RegistrationNumberEditDTO;
import my.project.vehiclefleetmanagement.service.car.RegistrationNumberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrationNumber")

public class RegistrationNumberController {

    private final RegistrationNumberService registrationNumberService;

    public RegistrationNumberController(RegistrationNumberService registrationNumberService) {
        this.registrationNumberService = registrationNumberService;
    }

    @GetMapping("/{registrationCertificateDataId}")
    public ResponseEntity<List<RegistrationNumberDTO>> getAllDriver(@PathVariable("registrationCertificateDataId") Long id) {
        return ResponseEntity.ok(
                this.registrationNumberService.getAllRegistrationNumber(id)
        );
    }

    @PostMapping
    public ResponseEntity<String> createDriver(@RequestBody RegistrationNumberCreateDTO registrationNumberCreateDTO) {

        boolean success = this.registrationNumberService.createRegistrationNumber(registrationNumberCreateDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Registration number already exist!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDriver(@PathVariable("id") Long id,@RequestBody RegistrationNumberEditDTO registrationNumberEditDTO) {

        boolean success = this.registrationNumberService.updateRegistrationNumber(id, registrationNumberEditDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration number not found!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationNumberDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(this.registrationNumberService.getRegistrationNumberById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        this.registrationNumberService.deleteRegistrationNumber(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
