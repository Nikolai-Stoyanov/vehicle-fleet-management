package my.project.vehiclefleetmanagement.model.dtos.car.carRecord;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarFuelDTO {
    @NotEmpty
    private String fuelType;
    @NotEmpty
    private int tankCapacity;
    private int distanceLimit;
    private int moneyLimit;
}
