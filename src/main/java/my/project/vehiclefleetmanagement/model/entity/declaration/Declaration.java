package my.project.vehiclefleetmanagement.model.entity.declaration;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
import my.project.vehiclefleetmanagement.model.entity.car.CarRecord;

import java.time.LocalDate;

@Entity
@Table(name = "declarations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Declaration extends BaseEntity {
    @OneToOne()
    private CarRecord carRecord;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String period;

    @Column( name = "last_mileage")
    private long lastMileage;

    @Column( name = "new_mileage")
    private long newMileage;

    @Column( name = "available_primary_fuel")
    private long availablePrimaryFuel;

    @Column(name = "available_additional_fuel")
    private long availableAdditionalFuel;

    @Column( name = "primary_engine_hour")
    private long primaryEngineHour;

    @Column(name = "primary_engine_min")
    private long primaryEngineMin;

    @Column(name = "additional_engine_hour")
    private long additionalEngineHour;

    @Column(name = "additional_engine_min")
    private long additionalEngineMin;
}
