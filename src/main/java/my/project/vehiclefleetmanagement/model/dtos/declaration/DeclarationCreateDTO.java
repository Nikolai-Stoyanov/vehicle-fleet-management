package my.project.vehiclefleetmanagement.model.dtos.declaration;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationCreateDTO {
  @NotEmpty
  private String registrationNumber;
  private String period;
  @NotEmpty
  private String date;
  @NotEmpty
  private int lastMileage;
  @NotEmpty
  private int newMileage;
  @NotEmpty
  private long fuelKind;
  @NotEmpty
  private long fuelSupplier;
  @NotEmpty
  private String fuelAmount;
  @NotEmpty
  private String fuelPrice;
}
