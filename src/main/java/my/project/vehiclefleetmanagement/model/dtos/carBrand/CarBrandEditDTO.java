package my.project.vehiclefleetmanagement.model.dtos.carBrand;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarBrandEditDTO {
  @NotEmpty
  private long id;
  @NotEmpty
  @Size(min = 5, max = 20)
  private String name;
  private String description;
  @NotEmpty
  private String company;
}
