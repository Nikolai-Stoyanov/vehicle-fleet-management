package my.project.vehiclefleetmanagement.model.entity.nomenclatures;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

import java.util.List;

@Entity
@Table(name = "fuel_suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelSupplier extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @Column()
    private String description;

    @ManyToMany()
    @JoinTable(
            name = "supplier_fuel",
            joinColumns = @JoinColumn(name = "fuel_supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "fuel_id")
    )
    private List<FuelEntity> fuelList;

    @Column()
    private boolean status;
}
