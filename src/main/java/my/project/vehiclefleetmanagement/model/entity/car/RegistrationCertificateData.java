package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarModel;
import my.project.vehiclefleetmanagement.model.enums.VehicleTypeEnum;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "registration_certificate_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationCertificateData extends BaseEntity {

    @Column(name = "registration_number",unique = true,nullable = false)
    private String registrationNumber;

    @Column(name = "first_registration",nullable = false)
    private LocalDate firstRegistration;

    @ManyToOne(optional = false)
    @JoinColumn(name = "model_id")
    private CarModel model;

    @Column(nullable = false,name = "frame_number")
    private String frameNumber;

    @Column(nullable = false,name = "engine_number")
    private String engineNumber;

    @Column(nullable = false,name = "engine_volume")
    private int engineVolume;

    @Column(nullable = false,name = "horse_power")
    private int horsePower;

    @Column(nullable = false,name = "engine_power")
    private int enginePower;

    @Column(name = "seating_capacity")
    private int seatingCapacity;

    @Column(nullable = false,name = "primary_color")
    private String primaryColor;

    @Column(name = "additional_color")
    private String additionalColor;

    @Column(nullable = false,name = "load_capacity")
    private int loadCapacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "vehicle_type")
    private VehicleTypeEnum vehicleType;
}
