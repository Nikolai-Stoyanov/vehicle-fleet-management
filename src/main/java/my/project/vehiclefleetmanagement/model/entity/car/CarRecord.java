package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.VehicleType;
import my.project.vehiclefleetmanagement.model.enums.DrivingCategoryType;

import java.util.List;

@Entity
@Table(name = "car_records")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRecord extends BaseEntity {
    @Column(nullable = false,name = "controlling_order")
    private String controllingOrder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "driving_category")
    private DrivingCategoryType drivingCategory;

    @Column(nullable = false)
    private String status;

    @Column()
    private String description;

    @ManyToOne(optional = false)
    private VehicleType vehicleType;

    @OneToOne(optional = false)
    private RegistrationCertificateData registrationCertificateData;

    @OneToOne(optional = false)
    private CarAdditionalInfo carAdditionalInfo;

    @OneToMany()
    private List<CarFuel> fuelList;

    @ManyToOne(optional = false)
    private Owner owner;

    @OneToMany()
    private List<Inspection> inspectionList;

    @OneToMany()
    private List<Insurance> insuranceList;

    @OneToMany()
    private List<Vignette> vignetteList;

    @ManyToMany()
    private List<Norm> normList;

    @OneToMany()
    private List<FuelCard> fuelCardList;

    @Column(name = "created_by")
    private String createdBy;
    @Column( name = "created_at")
    private Long createdAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column( name = "updated_at")
    private Long updatedAt;

    @OneToOne()
    private Mileage mileage;

    @OneToOne()
    private EngineHour primaryEngine;

    @OneToOne()
    private EngineHour additionalEngine;
}
