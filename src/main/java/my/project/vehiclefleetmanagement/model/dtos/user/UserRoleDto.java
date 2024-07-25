package my.project.vehiclefleetmanagement.model.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.project.vehiclefleetmanagement.model.enums.UserRoleEnum;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleDto {

    private UserRoleEnum role;
}
