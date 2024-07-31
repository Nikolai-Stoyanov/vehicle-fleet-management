package my.project.vehiclefleetmanagement.model.dtos.declaration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.entity.car.CarPerson;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationDTO {
  private long id;
  private long carRecordId;
  private String period;
  private LocalDate date;
  private long lastMileage;
  private long newMileage;
  private CarPerson responsible;
  private String registrationNumber;
  private List<DeclarationFuelDTO> fuelsList;
}
