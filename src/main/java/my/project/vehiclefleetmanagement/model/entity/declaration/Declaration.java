package my.project.vehiclefleetmanagement.model.entity.declaration;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
import my.project.vehiclefleetmanagement.model.entity.car.CarRecord;
import my.project.vehiclefleetmanagement.model.entity.car.DeclarationFuel;

import java.time.LocalDate;
import java.util.List;

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
    private long lastMileage;

    @Column( name = "new_mileage")
    private long newMileage;

    @OneToMany(mappedBy = "declaration")
    private List<DeclarationFuel> fuels;

}
