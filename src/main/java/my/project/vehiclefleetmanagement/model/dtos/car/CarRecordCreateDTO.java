package my.project.vehiclefleetmanagement.model.dtos.car;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRecordCreateDTO {
  @NotEmpty
  private String drivingCategory;
  private String description;
  @NotEmpty
  private Long totalMileage;
  @NotEmpty
  private Long developmentFromMileage;
  @NotEmpty
  private Long developmentToMileage;
  @NotEmpty
  private String fuelCard;
  @NotEmpty
  private String fuelType;
  @NotEmpty
  private RegistrationCertificateDataDTO registrationCertificateData;
  @NotEmpty
  private boolean status;
  @NotEmpty
  private String owner;
  @NotEmpty
  private String department;
  @NotEmpty
  private String stay;
  @NotEmpty
  private CarPersonDTO responsible;
  @NotEmpty
  private CarPersonDTO driver;
}
