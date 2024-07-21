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
  @Size(min = 5, max = 20)
  private String name;
  @NotEmpty
  private String phoneNumber;
  @NotEmpty
  private boolean status;
  @NotEmpty
  private long owner;
}
