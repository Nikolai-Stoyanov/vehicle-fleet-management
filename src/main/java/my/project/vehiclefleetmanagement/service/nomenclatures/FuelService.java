package my.project.vehiclefleetmanagement.service.nomenclatures;

import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelListDTO;

import java.util.List;

public interface FuelService {

    boolean createFuel(FuelCreateDTO fuelCreateDTO);

    List<FuelListDTO> getAllFuels();

    FuelDTO getFuelById(Long id);

    void deleteFuel(Long id);

    boolean updateFuel(Long id, FuelEditDTO fuelEditDTO);
}
