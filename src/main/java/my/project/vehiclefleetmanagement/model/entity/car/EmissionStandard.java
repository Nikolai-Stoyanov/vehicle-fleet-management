package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

@Entity
@Table(name = "emission_standards")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmissionStandard extends BaseEntity {
    @Column(unique = true, nullable = false)
    private int level;

    @Column(nullable = false)
    private String document;

}
