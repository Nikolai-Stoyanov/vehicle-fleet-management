package my.project.vehiclefleetmanagement.model.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO {
  private int id;
  private String username;
  private String email;
  private List<String> roles;
}
