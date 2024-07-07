package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

@Entity
@Table(name = "car_additional_infos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarAdditionalInfo extends BaseEntity {

    @Column(nullable = false, name = "is_active")
    private boolean isActive;

    @Column(nullable = false, name = "is_locked")
    private boolean isLocked;

    @Column(nullable = false, name = "is_dds")
    private boolean isDDS;

    @Column(nullable = false, name = "report_distance_gps")
    private boolean reportDistanceGPS;

    @Column(nullable = false, name = "report_time_gps")
    private boolean reportTimeGPS;

    @Column()
    private Long ownNorm;
}
