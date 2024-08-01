package my.project.vehiclefleetmanagement.model.dtos.car;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRecordEditDTO {
  @NotNull
  private int id;
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
  private String createdBy;
  private String createdAt;
  private String updatedBy;
  private String updatedAt;
}
