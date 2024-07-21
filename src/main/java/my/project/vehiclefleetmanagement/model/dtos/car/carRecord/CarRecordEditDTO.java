package my.project.vehiclefleetmanagement.model.dtos.car.carRecord;


import jakarta.validation.constraints.NotEmpty;
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
public class CarRecordEditDTO {
  @NotEmpty
  private int id;
  @NotEmpty
  private String drivingCategory;
  private String description;
  @NotEmpty
  private MileageDTO mileage;
  @NotEmpty
  private List<FuelCardDTO> fuelCardList;
  @NotEmpty
  private OwnerDTO owner;
  @NotEmpty
  private List<CarFuelDTO> fuelList;
  @NotEmpty
  private RegistrationCertificateDataDTO registrationCertificateData;
  @NotEmpty
  private boolean status;
}
