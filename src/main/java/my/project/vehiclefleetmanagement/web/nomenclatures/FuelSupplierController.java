package my.project.vehiclefleetmanagement.web.nomenclatures;


import jakarta.validation.Valid;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierListDTO;
import my.project.vehiclefleetmanagement.service.nomenclatures.FuelSupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fuelSupplier")

public class FuelSupplierController {

    private final FuelSupplierService fuelSupplierService;

    public FuelSupplierController(FuelSupplierService fuelSupplierService) {
        this.fuelSupplierService = fuelSupplierService;
    }

    @GetMapping
    public ResponseEntity<List<FuelSupplierListDTO>> getAllFuelSupplier() {
        return ResponseEntity.ok(fuelSupplierService.getAllFuelSuppliers());
    }

    @PostMapping
    public ResponseEntity<String> createFuelSupplier(@RequestBody @Valid FuelSupplierCreateDTO fuelSupplierCreateDTO) {
        fuelSupplierService.createFuelSupplier(fuelSupplierCreateDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFuelSupplier(@PathVariable("id") Long id, @RequestBody @Valid FuelSupplierEditDTO fuelSupplierEditDTO) {
        fuelSupplierService.updateFuelSupplier(id, fuelSupplierEditDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelSupplierDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fuelSupplierService.getFuelSupplierById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        fuelSupplierService.deleteFuelSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
