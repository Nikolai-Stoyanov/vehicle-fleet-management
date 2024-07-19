package my.project.vehiclefleetmanagement.model.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
  @NotEmpty
  @Size(min = 5, max = 20)
  private String username;
  @NotEmpty
  private String confirmPassword;
  @NotEmpty
  private String password;
  @NotEmpty
  @Email
  private String email;
}
