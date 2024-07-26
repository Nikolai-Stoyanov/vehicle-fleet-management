package my.project.vehiclefleetmanagement.web.car;

import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonEditDTO;
import my.project.vehiclefleetmanagement.service.car.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")

public class DriversController {

    private final DriverService driverService;

    public DriversController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<List<CarPersonDTO>> getAllDriver(@PathVariable("ownerId") Long id) {
        return ResponseEntity.ok(
                this.driverService.getAllDriver(id)
        );
    }

    @PostMapping
    public ResponseEntity<String> createDriver(@RequestBody CarPersonCreateDTO carPersonCreateDTO) {

        boolean success = this.driverService.createDriver(carPersonCreateDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Driver already exist!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDriver(@PathVariable("id") Long id,@RequestBody CarPersonEditDTO carPersonEditDTO) {

        boolean success = this.driverService.updateDriver(id, carPersonEditDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Driver not found!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarPersonDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(this.driverService.getDriverById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        this.driverService.deleteDriver(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
