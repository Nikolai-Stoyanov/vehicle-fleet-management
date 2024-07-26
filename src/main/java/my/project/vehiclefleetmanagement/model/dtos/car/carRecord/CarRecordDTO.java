package my.project.vehiclefleetmanagement.model.dtos.car.carRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.dtos.car.fuelCard.FuelCardDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRecordDTO {
  private int id;
  private String drivingCategory;
  private String description;
  private MileageDTO mileage;
  private List<FuelCardDTO> fuelCardList;
  private OwnerDTO owner;
  private List<CarFuelDTO> fuelList;
  private RegistrationCertificateDataDTO registrationCertificateData;
  private boolean status;
  private String createdBy;
  private Long createdAt;
  private String updatedBy;
  private Long updatedAt;
}
