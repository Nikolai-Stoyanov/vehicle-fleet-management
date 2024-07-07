package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

import java.util.List;

@Entity
@Table(name = "owners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column( nullable = false)
    private String headquarters;

    @Column( nullable = false)
    private String department;

    @Column()
    private String subgroup;

    @Column(name = "sub_subgroup")
    private String subSubgroup;

    @Column(name = "cost_center")
    private String costCenter;

    @Column()
    private String stay;

    @ManyToOne(optional = false)
    private CarPerson personResponsible;

    @ManyToMany()
    private List<CarPerson> drivers;

    @OneToMany(mappedBy = "owner")
    private List<CarRecord> carIdList;
}
