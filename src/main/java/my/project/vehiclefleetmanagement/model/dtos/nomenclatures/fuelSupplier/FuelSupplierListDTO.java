package my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelSupplierListDTO {
  private int id;
  private String name;
  private String description;
  private List<String> fuelList;
  private boolean status;
}
