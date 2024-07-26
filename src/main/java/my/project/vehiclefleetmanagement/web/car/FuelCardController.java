package my.project.vehiclefleetmanagement.web.car;

import my.project.vehiclefleetmanagement.model.dtos.car.fuelCard.FuelCardCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.fuelCard.FuelCardDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.fuelCard.FuelCardEditDTO;
import my.project.vehiclefleetmanagement.service.car.FuelCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fuelCard")

public class FuelCardController {

    private final FuelCardService fuelCardService;

    public FuelCardController(FuelCardService fuelCardService) {
        this.fuelCardService = fuelCardService;
    }

    @GetMapping("/{cardRecordId}")
    public ResponseEntity<List<FuelCardDTO>> getAllDriver(@PathVariable("cardRecordId") Long id) {
        return ResponseEntity.ok(
                this.fuelCardService.getAllFuelCards(id)
        );
    }

    @PostMapping
    public ResponseEntity<String> createDriver(@RequestBody FuelCardCreateDTO fuelCardCreateDTO) {

        boolean success = this.fuelCardService.createFuelCard(fuelCardCreateDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Driver already exist!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDriver(@PathVariable("id") Long id,@RequestBody FuelCardEditDTO fuelCardEditDTO) {

        boolean success = this.fuelCardService.updateFuelCard(id,fuelCardEditDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Driver not found!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelCardDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(this.fuelCardService.getFuelCardById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        this.fuelCardService.deleteFuelCard(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
