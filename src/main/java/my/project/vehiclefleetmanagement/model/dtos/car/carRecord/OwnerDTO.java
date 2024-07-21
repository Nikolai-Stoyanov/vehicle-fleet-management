package my.project.vehiclefleetmanagement.model.dtos.car.carRecord;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String headquarters;
    @NotEmpty
    private String department;
    @NotEmpty
    private String subgroup;
    private String subSubgroup;
    @NotEmpty
    private String costCenter;
    @NotEmpty
    private String stay;
    @NotEmpty
    private long personResponsible;
    @NotEmpty
    private List<Long> drivers;
}
