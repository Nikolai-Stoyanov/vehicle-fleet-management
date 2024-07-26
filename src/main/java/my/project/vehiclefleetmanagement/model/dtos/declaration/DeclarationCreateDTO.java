package my.project.vehiclefleetmanagement.model.dtos.declaration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.entity.car.DeclarationFuel;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationCreateDTO {

  @NotEmpty
  private long carRecordId;
  private String period;
  @NotEmpty
  private LocalDate date;
  @NotEmpty
  private long lastMileage;
  @NotEmpty
  private long newMileage;
  @NotEmpty
  private List<DeclarationFuel> fuels;
}
