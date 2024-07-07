package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
import my.project.vehiclefleetmanagement.model.enums.FuelType;

@Entity
@Table(name = "norms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Norm extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long norm;

    @Column(nullable = false, name = "is_active")
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "fuel_type")
    private FuelType fuelType;

}
