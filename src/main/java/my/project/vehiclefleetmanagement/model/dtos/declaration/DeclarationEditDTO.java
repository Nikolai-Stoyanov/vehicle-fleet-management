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
public class DeclarationEditDTO {
  @NotEmpty
  private long id;
  @NotEmpty
  private String registrationNumber;
  private String period;
  @NotEmpty
  private String date;
  @NotEmpty
  private long lastMileage;
  @NotEmpty
  private long newMileage;
  @NotEmpty
  private long fuelKind;
  @NotEmpty
  private long fuelSupplier;
  @NotEmpty
  private double fuelAmount;
  @NotEmpty
  private double fuelPrice;
  @NotEmpty
  private String createdBy;
  @NotEmpty
  private String createdAt;
  private String updatedBy;
  private String updatedAt;
}
