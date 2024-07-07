package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.enums.DocumentType;

@Entity
@Table(name = "inspections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inspection extends BaseCarDocumentEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "document_type")
    private DocumentType documentType;

}
