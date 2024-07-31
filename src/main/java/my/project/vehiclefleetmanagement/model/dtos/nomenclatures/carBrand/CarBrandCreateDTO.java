package my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarBrandCreateDTO {
  @NotEmpty
  @Size(min = 3, max = 20)
  private String name;
  private String description;
  @NotEmpty
  private String company;
  private boolean status;
}
