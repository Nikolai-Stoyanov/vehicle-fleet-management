package my.project.vehiclefleetmanagement.model.entity.nomenclatures;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

import java.time.LocalDate;

@Entity
@Table(name = "car_models")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModel extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
    @Column()
    private String description;
    @Column()
    private LocalDate year;
    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id")
    private CarBrand brand;
    @Column()
    private boolean status;
}
