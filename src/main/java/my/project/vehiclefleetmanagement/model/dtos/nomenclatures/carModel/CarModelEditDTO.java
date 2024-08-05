package my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModelEditDTO {
  @NotNull
  private int id;
  @NotEmpty
  @Size(min = 5, max = 20)
  private String name;
  private String description;
  @NotEmpty
  private String year;
  @NotNull
  private int brand;
  private boolean status;
}
