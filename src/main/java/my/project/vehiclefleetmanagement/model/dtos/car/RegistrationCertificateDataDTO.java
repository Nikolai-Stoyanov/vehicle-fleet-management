package my.project.vehiclefleetmanagement.model.dtos.car;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelListDTO;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationCertificateDataDTO {
    @NotEmpty
    private String registrationNumber;
    @NotEmpty
    private String firstRegistration;
    @NotEmpty
    private CarModelListDTO model;
    @NotEmpty
    private String frameNumber;
    @NotEmpty
    private String engineNumber;
    @NotNull
    private int engineVolume;
    @NotNull
    private int horsePower;
    @NotNull
    private int enginePower;
    @NotNull
    private int seatingCapacity;
    @NotEmpty
    private String primaryColor;
    private String additionalColor;
    private int loadCapacity;
    @NotEmpty
    private String vehicleType;
}
