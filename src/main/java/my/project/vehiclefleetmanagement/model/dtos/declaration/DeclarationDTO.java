package my.project.vehiclefleetmanagement.model.dtos.declaration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationDTO {
  private long id;
  private String period;
  private String date;
  private int lastMileage;
  private int newMileage;
  private String responsible;
  private String registrationNumber;
  private String fuelType;
  private FuelDTO fuelKind;
  private FuelSupplierDTO fuelSupplier;
  private double fuelAmount;
  private double fuelPrice;
  private String createdBy;
  private String createdAt;
  private String updatedBy;
  private String updatedAt;
}
