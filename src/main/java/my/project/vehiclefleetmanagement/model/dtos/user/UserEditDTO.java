package my.project.vehiclefleetmanagement.model.dtos.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDTO {
  private Long id;
  @NotEmpty
  @Size(min = 5, max = 20)
  private String username;
  @Email
  private String email;
  private List<UserRoleDto> roles;
}
