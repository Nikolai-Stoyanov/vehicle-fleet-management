package my.project.vehiclefleetmanagement.model.dtos.car;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
  @NotNull
  private int totalMileage;
  @NotNull
  private int developmentFromMileage;
  @NotNull
  private int developmentToMileage;
  @NotEmpty
  private String fuelCard;
  @NotEmpty
  private String fuelType;
  @NotNull
  private RegistrationCertificateDataDTO registrationCertificateData;
  private boolean status;
  @NotEmpty
  private String owner;
  @NotEmpty
  private String department;
  @NotEmpty
  private String stay;
  @NotNull
  private CarPersonDTO responsible;
  @NotNull
  private CarPersonDTO driver;
}
