package my.project.vehiclefleetmanagement.model.entity.nomenclatures;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

import java.util.List;

@Entity
@Table(name = "vehicle_makes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleMake extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
    @Column()
    private String description;
    @Column()
    private String company;
    @OneToMany(mappedBy = "make")
    private List<VehicleModel> models;
}
