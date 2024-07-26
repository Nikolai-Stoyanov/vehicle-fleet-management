package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
import my.project.vehiclefleetmanagement.model.enums.DrivingCategoryType;

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

    @OneToMany(mappedBy = "carRecord")
    private List<CarFuel> fuelList;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(mappedBy = "carRecord")
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
    @JoinColumn(name = "mileage_id", referencedColumnName = "id")
    private Mileage mileage;

}
