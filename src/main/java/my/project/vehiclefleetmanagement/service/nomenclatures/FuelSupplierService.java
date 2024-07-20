package my.project.vehiclefleetmanagement.service.nomenclatures;

import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierListDTO;

import java.util.List;

public interface FuelSupplierService {

    boolean createFuelSupplier(FuelSupplierCreateDTO fuelCreateDTO);

    List<FuelSupplierListDTO> getAllFuelSuppliers();

    FuelSupplierDTO getFuelSupplierById(Long id);

    void deleteFuelSupplier(Long id);

    boolean updateFuelSupplier(Long id, FuelSupplierEditDTO fuelEditDTO);
}
