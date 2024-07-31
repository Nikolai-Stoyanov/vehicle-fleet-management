package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
import my.project.vehiclefleetmanagement.model.enums.DrivingCategoryType;
import my.project.vehiclefleetmanagement.model.enums.FuelType;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "car_records")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRecord extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "driving_category")
    private DrivingCategoryType drivingCategory;
    @Column(nullable = false)
    private boolean status;
    @Column()
    private String description;
    @OneToOne(optional = false)
    @JoinColumn(name = "registration_certificate_data_id", referencedColumnName = "id")
    private RegistrationCertificateData registrationCertificateData;
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;
    @Column(name = "fuel_card")
    private String fuelCard;
    @Column(name = "created_by")
    private String createdBy;
    @Column( name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column( name = "updated_at")
    private LocalDate updatedAt;
    @Column(nullable = false)
    private Long totalMileage;
    @Column(nullable = false)
    private Long developmentFromMileage;
    @Column(nullable = false)
    private Long developmentToMileage;
    @Column( nullable = false)
    private String owner;
    @Column( nullable = false)
    private String department;
    @Column()
    private String stay;
    @ManyToOne(optional = false)
    private CarPerson responsible;
    @ManyToOne(optional = false)
    private CarPerson driver;
}
