package my.project.vehiclefleetmanagement.model.dtos.car.carPerson;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarPersonCreateDTO {
  @NotEmpty
  @Size(min = 3, max = 20)
  private String firstName;
  @NotEmpty
  @Size(min = 3, max = 20)
  private String lastName;
  @NotEmpty
  private String phoneNumber;
  private boolean status;
}
