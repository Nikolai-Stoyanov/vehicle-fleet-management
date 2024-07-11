package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
@Entity
@Table(name = "car_persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarPerson extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false, name = "is_active")
    private boolean isActive;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private Owner owner;
}
