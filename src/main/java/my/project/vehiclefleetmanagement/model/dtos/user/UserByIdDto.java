package my.project.vehiclefleetmanagement.model.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserByIdDto {

    private Long id;
    private String username;
    private String email;
    private List<UserRoleDto> roles;
}
