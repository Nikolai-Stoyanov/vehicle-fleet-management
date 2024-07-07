package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseCarDocumentEntity extends BaseEntity {
    @Column(nullable = false, name = "date_from")
    private Long dateFrom;
    @Column(nullable = false, name = "date_to")
    private Long dateTo;
    @Column(nullable = false)
    private boolean locked;
    @Column(nullable = false)
    private boolean active;
    @Column(name = "created_by")
    private String createdBy;
    @Column( name = "created_at")
    private Long createdAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column( name = "updated_at")
    private Long updatedAt;
}
