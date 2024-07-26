package my.project.vehiclefleetmanagement.model.dtos.car.carRecord;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.dtos.car.registrationNumber.RegistrationNumberDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelDTO;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationCertificateDataDTO {
    @NotEmpty
    private List<RegistrationNumberDTO> registrationNumbers;
    @NotEmpty
    private CarModelDTO model;
    @NotEmpty
    private String frameNumber;
    @NotEmpty
    private String engineNumber;
    @NotEmpty
    private int engineVolume;
    @NotEmpty
    private int horsePower;
    @NotEmpty
    private int enginePower;
    @NotEmpty
    private int seatingCapacity;
    @NotEmpty
    private String primaryColor;
    @NotEmpty
    private String additionalColor;
    private int loadCapacity;
    @NotEmpty
    private LocalDate firstRegistrationDate;
    @NotEmpty
    private String vehicleType;
}
