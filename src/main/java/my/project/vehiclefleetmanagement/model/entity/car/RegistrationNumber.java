package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

import java.time.LocalDate;

@Entity
@Table(name = "registration_numbers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationNumber extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String registration;

    @Column(nullable = false,name = "registration_date")
    private LocalDate registrationDate;

    @Column(nullable = false)
    private boolean isActive;
}
