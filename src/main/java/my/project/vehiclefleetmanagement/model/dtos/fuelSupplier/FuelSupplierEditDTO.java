package my.project.vehiclefleetmanagement.model.dtos.fuelSupplier;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelSupplierEditDTO {
  @NotEmpty
  private long id;
  @NotEmpty
  @Size(min = 5, max = 20)
  private String name;
  private String description;
  private boolean status;
  private List<Long> fuelList;
}
