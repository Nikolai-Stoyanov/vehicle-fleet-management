package my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.enums.FuelType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelCreateDTO {
  @NotEmpty
  private String name;
  private String description;
  private boolean status;
}
