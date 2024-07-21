package my.project.vehiclefleetmanagement.model.dtos.car.carRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.dtos.car.fuelCard.FuelCardDTO;
import my.project.vehiclefleetmanagement.model.entity.car.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRecordListDTO {
  private int id;
  private String drivingCategory;
  private String description;
  private String ownerName;
  private String registrationNumber;
  private String carBrand;
  private String carModel;
  private String responsibleName;
  private boolean status;
}
