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
public class DeclarationEditDTO {
  @NotNull
  private long id;
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
  private long fuelKind;
  @NotNull
  private long fuelSupplier;
  @NotNull
  private double fuelAmount;
  @NotNull
  private double fuelPrice;
  private String createdBy;
  private String createdAt;
  private String updatedBy;
  private String updatedAt;
}
