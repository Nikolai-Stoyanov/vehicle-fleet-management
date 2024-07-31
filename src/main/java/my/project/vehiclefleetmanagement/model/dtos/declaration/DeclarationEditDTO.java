package my.project.vehiclefleetmanagement.model.dtos.declaration;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierDTO;

import java.time.LocalDate;

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
