package my.project.vehiclefleetmanagement.web.nomenclatures;

import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelListDTO;
import my.project.vehiclefleetmanagement.service.nomenclatures.FuelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fuel")

public class FuelController {

    private final FuelService fuelService;

    public FuelController(FuelService fuelService) {

        this.fuelService = fuelService;
    }


    @GetMapping
    public ResponseEntity<List<FuelListDTO>> getAllFuel() {
        return ResponseEntity.ok(
                fuelService.getAllFuels()
        );
    }

    @PostMapping
    public ResponseEntity<String> createFuel(@RequestBody FuelCreateDTO fuelCreateDTO) {

        boolean success = fuelService.createFuel(fuelCreateDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Fuel already exist!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFuel(@PathVariable("id") Long id,@RequestBody FuelEditDTO fuelEditDTO) {

        boolean success = fuelService.updateFuel(id,fuelEditDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fuel not found!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(fuelService.getFuelById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        fuelService.deleteFuel(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
