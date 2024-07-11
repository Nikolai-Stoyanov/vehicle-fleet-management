package my.project.vehiclefleetmanagement.model.entity.nomenclatures;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
import my.project.vehiclefleetmanagement.model.enums.FuelType;

@Entity
@Table(name = "fuels")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fuel extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "fuel_type")
    private FuelType fuelType;

    @Column()
    private String description;

    @ManyToOne
    @JoinColumn(name = "fuelSupplier_id")
    private FuelSupplier fuelSupplier;
}
