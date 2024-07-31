package my.project.vehiclefleetmanagement.model.dtos.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationNumberDTO {
    private int id;
    private String registrationNumber;
}
