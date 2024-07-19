package my.project.vehiclefleetmanagement.service;

import my.project.vehiclefleetmanagement.model.dtos.fuelSupplier.FuelSupplierCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.fuelSupplier.FuelSupplierDTO;
import my.project.vehiclefleetmanagement.model.dtos.fuelSupplier.FuelSupplierEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.fuelSupplier.FuelSupplierListDTO;

import java.util.List;

public interface FuelSupplierService {

    boolean createFuelSupplier(FuelSupplierCreateDTO fuelCreateDTO);

    List<FuelSupplierListDTO> getAllFuelSuppliers();

    FuelSupplierDTO getFuelSupplierById(Long id);

    void deleteFuelSupplier(Long id);

    boolean updateFuelSupplier(Long id, FuelSupplierEditDTO fuelEditDTO);
}
