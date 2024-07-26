package my.project.vehiclefleetmanagement.model.entity.nomenclatures;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

@Entity
@Table(name = "fuels")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column()
    private String description;

    @Column()
    private boolean status;
}
