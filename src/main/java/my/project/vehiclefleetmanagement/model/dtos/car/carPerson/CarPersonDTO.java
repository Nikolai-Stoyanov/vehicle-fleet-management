package my.project.vehiclefleetmanagement.model.dtos.car.carPerson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarPersonDTO {
  private int id;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String FullName;
  private boolean status;
}
