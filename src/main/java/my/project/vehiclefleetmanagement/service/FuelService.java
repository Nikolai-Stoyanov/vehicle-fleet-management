package my.project.vehiclefleetmanagement.service;

import my.project.vehiclefleetmanagement.model.dtos.fuel.FuelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.fuel.FuelDTO;
import my.project.vehiclefleetmanagement.model.dtos.fuel.FuelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.fuel.FuelListDTO;

import java.util.List;

public interface FuelService {

    boolean createFuel(FuelCreateDTO fuelCreateDTO);

    List<FuelListDTO> getAllFuels();

    FuelDTO getFuelById(Long id);

    void deleteFuel(Long id);

    boolean updateFuel(Long id, FuelEditDTO fuelEditDTO);
}
