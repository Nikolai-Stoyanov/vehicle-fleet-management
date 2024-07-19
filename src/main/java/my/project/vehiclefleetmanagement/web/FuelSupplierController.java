package my.project.vehiclefleetmanagement.web;


import my.project.vehiclefleetmanagement.model.dtos.fuelSupplier.FuelSupplierCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.fuelSupplier.FuelSupplierDTO;
import my.project.vehiclefleetmanagement.model.dtos.fuelSupplier.FuelSupplierEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.fuelSupplier.FuelSupplierListDTO;
import my.project.vehiclefleetmanagement.service.FuelSupplierService;
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
        return ResponseEntity.ok(
                fuelSupplierService.getAllFuelSuppliers()
        );
    }

    @PostMapping
    public ResponseEntity<String> createFuelSupplier(@RequestBody FuelSupplierCreateDTO fuelSupplierCreateDTO) {

        boolean success = fuelSupplierService.createFuelSupplier(fuelSupplierCreateDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Supplier already exist!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFuelSupplier(@PathVariable("id") Long id,@RequestBody FuelSupplierEditDTO fuelSupplierEditDTO) {

        boolean success = fuelSupplierService.updateFuelSupplier(id,fuelSupplierEditDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fuel not found!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelSupplierDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(fuelSupplierService.getFuelSupplierById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        fuelSupplierService.deleteFuelSupplier(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
