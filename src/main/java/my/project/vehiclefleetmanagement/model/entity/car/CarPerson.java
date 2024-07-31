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

    @Column(nullable = false,name = "first_name")
    private String firstName;

    @Column(nullable = false,name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "phone_number",unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean status;

    @Transient
    private String FullName;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
