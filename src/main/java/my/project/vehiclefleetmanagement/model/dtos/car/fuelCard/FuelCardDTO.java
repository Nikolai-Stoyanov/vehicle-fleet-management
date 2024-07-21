package my.project.vehiclefleetmanagement.model.dtos.car.fuelCard;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelCardDTO {
  @NotEmpty
  private String cardNumber;
  private String moneyLimit;
  @NotEmpty
  private boolean status;
  private long carRecord;
}
