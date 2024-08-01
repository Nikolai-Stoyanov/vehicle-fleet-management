package my.project.vehiclefleetmanagement.model.dtos.declaration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
  @NotNull
  private int lastMileage;
  @NotNull
  private int newMileage;
  @NotNull
  private int fuelKind;
  @NotNull
  private int fuelSupplier;
  @NotEmpty
  private double fuelAmount;
  @NotEmpty
  private double fuelPrice;
}
