package my.project.vehiclefleetmanagement.web.nomenclatures;

import jakarta.validation.Valid;
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
        return ResponseEntity.ok(fuelService.getAllFuels());
    }

    @PostMapping
    public ResponseEntity<String> createFuel(@RequestBody @Valid FuelCreateDTO fuelCreateDTO) {
        fuelService.createFuel(fuelCreateDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFuel(@PathVariable("id") Long id, @RequestBody @Valid FuelEditDTO fuelEditDTO) {
        fuelService.updateFuel(id, fuelEditDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fuelService.getFuelById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        fuelService.deleteFuel(id);
        return ResponseEntity.noContent().build();
    }
}
