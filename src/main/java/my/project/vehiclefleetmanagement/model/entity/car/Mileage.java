package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

@Entity
@Table(name = "mileages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mileage extends BaseEntity {
    @Column(nullable = false)
    private Long totalMileage;
    @Column(nullable = false)
    private Long developmentFromMileage;
    @Column(nullable = false)
    private Long developmentToMileage;
}
