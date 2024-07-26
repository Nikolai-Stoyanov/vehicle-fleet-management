package my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelSupplierDTO {
  private int id;
  private String name;
  private String description;
  private List<FuelDTO> fuelList;
  private boolean status;
}
