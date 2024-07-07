package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
import my.project.vehiclefleetmanagement.model.enums.FuelType;

@Entity
@Table(name = "car_fuels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarFuel extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "fuel_type")
    private FuelType fuelType;

    @Column(nullable = false, name = "tank_capacity")
    private int tankCapacity;

    @Column(name = "distance_limit")
    private int distanceLimit;

    @Column(name = "money_limit")
    private int moneyLimit;
}
