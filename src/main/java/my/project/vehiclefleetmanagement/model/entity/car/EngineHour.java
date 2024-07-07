package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
@Entity
@Table(name = "engine_hour")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EngineHour extends BaseEntity {
    @Column(nullable = false, name = "total_hour")
    private Long totalHour;
    @Column(nullable = false, name = "total_min")
    private Long totalMin;
    @Column(nullable = false, name = "development_hour")
    private Long developmentHour;
    @Column(nullable = false, name = "development_minutes")
    private Long developmentMinutes;
}
