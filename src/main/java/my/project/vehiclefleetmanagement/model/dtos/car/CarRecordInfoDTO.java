package my.project.vehiclefleetmanagement.model.dtos.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRecordInfoDTO {

  private String fuelType;
  private String registrationNumber;
  private String responsible;
  private int lastMileage;
}
