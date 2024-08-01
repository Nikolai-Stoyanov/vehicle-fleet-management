package my.project.vehiclefleetmanagement.model.dtos.car;

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
public class CarRecordDTO {
  private int id;
  private String drivingCategory;
  private String description;
  private int totalMileage;
  private int developmentFromMileage;
  private int developmentToMileage;
  private String fuelCard;
  private String fuelType;
  private RegistrationCertificateDataDTO registrationCertificateData;
  private boolean status;
  private String createdBy;
  private String createdAt;
  private String updatedBy;
  private String updatedAt;
  private String owner;
  private String department;
  private String stay;
  private CarPersonDTO responsible;
  private CarPersonDTO driver;
}
