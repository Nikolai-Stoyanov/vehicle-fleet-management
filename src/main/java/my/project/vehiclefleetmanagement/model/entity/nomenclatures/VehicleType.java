package my.project.vehiclefleetmanagement.model.entity.nomenclatures;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
import my.project.vehiclefleetmanagement.model.enums.DistributionType;

@Entity
@Table(name = "vehicle_types")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleType extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column()
    private String description;

    @Enumerated(EnumType.STRING)
    @Column( name = "distribution_type")
    private DistributionType distributionType;
}
