package my.project.vehiclefleetmanagement.model.entity.car;
import jakarta.persistence.*;
import lombok.*;
import my.project.vehiclefleetmanagement.model.enums.DocumentType;
import my.project.vehiclefleetmanagement.model.enums.VignetteType;

@Entity
@Table(name = "vignettes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vignette extends BaseCarDocumentEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "document_type")
    private DocumentType documentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "vignette_type")
    private VignetteType vignetteType;

}
