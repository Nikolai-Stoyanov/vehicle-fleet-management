package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
import my.project.vehiclefleetmanagement.model.enums.FuelType;

@Entity
@Table(name = "declaration_fuels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationFuel extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "fuel_type")
    private FuelType fuelType;

    @Column(nullable = false, name = "tank_capacity")
    private int tankCapacity;

    @Column(name = "distance_limit")
    private int distanceLimit;

    @Column(name = "distance_remaining")
    private int distanceRemaining;

    @Column(name = "fuel_remaining")
    private int fuelRemaining;

    @Column(name = "fuel_consumption")
    private int fuelConsumption;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "money_limit")
    private int moneyLimit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "declaration_id")
    private CarRecord declaration;
}
