package my.project.vehiclefleetmanagement.model.entity.nomenclatures;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

import java.util.List;

@Entity
@Table(name = "car_brands")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarBrand extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
    @Column()
    private String description;
    @Column()
    private String company;
    @OneToMany(mappedBy = "brand")
    private List<CarModel> models;
}
