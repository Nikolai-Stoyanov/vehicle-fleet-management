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
    @ManyToOne()
    private CarRecord carRecord;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String period;
    @Column( name = "last_mileage")
    private int lastMileage;
    @Column( name = "new_mileage")
    private int newMileage;
    @Column( name = "fuel_kind_id")
    private long fuelKind;
    @Column( name = "fuel_supplier_id")
    private long fuelSupplier;
    @Column( name = "fuel_amount")
    private double fuelAmount;
    @Column( name = "fuel_price")
    private double fuelPrice;
    @Column(name = "created_by")
    private String createdBy;
    @Column( name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column( name = "updated_at")
    private LocalDate updatedAt;
}
