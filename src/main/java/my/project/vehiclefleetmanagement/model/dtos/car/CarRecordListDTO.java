package my.project.vehiclefleetmanagement.model.dtos.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRecordListDTO {
  private int id;
  private String drivingCategory;
  private String description;
  private String owner;
  private String registrationNumber;
  private String carBrand;
  private String carModel;
  private String responsible;
  private boolean status;
}
