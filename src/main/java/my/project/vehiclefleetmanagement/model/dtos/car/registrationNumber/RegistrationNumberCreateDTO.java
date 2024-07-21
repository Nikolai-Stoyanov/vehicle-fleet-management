package my.project.vehiclefleetmanagement.model.dtos.car.registrationNumber;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationNumberCreateDTO {
  @NotEmpty
  private String registration;
  @NotEmpty
  private String registrationDate;
  @NotEmpty
  private boolean status;
  @NotEmpty
  private long registrationCertificateData;
}
