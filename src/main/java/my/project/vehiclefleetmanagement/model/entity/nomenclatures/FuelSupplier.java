package my.project.vehiclefleetmanagement.model.entity.nomenclatures;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

import java.util.List;

@Entity
@Table(name = "fuel-suppliers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelSupplier extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @Column()
    private String description;

    @OneToMany()
    private List<Fuel> fuelList;
}
