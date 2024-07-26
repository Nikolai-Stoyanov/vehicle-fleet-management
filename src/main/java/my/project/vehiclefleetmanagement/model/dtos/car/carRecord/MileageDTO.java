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
public class MileageDTO {
    @NotEmpty
    private Long totalMileage;
    @NotEmpty
    private Long developmentFromMileage;
    @NotEmpty
    private Long developmentToMileage;
}
