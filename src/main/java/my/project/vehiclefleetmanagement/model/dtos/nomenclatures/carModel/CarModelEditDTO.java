package my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModelEditDTO {
  @NotEmpty
  private long id;
  @NotEmpty
  @Size(min = 5, max = 20)
  private String name;
  private String description;
  @NotEmpty
  private LocalDate year;
  @NotEmpty
  private long brand;
  @NotEmpty
  private boolean status;
}
