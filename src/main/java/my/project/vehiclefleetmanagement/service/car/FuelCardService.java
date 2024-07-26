package my.project.vehiclefleetmanagement.service.car;

import my.project.vehiclefleetmanagement.model.dtos.car.fuelCard.FuelCardCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.fuelCard.FuelCardDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.fuelCard.FuelCardEditDTO;

import java.util.List;

public interface FuelCardService {

    boolean createFuelCard(FuelCardCreateDTO fuelCardCreateDTO);

    List<FuelCardDTO> getAllFuelCards(Long id);

    FuelCardDTO getFuelCardById(Long id);

    void deleteFuelCard(Long id);

    boolean updateFuelCard(Long id, FuelCardEditDTO fuelCardEditDTO);
}
