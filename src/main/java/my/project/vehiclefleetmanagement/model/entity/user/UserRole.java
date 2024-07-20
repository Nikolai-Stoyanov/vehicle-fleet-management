package my.project.vehiclefleetmanagement.model.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import my.project.vehiclefleetmanagement.model.enums.UserRoleEnum;

@Entity
@Table(name = "roles")
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(unique = true)
  @Enumerated(EnumType.STRING)
  private UserRoleEnum role;

  public Long getId() {
    return id;
  }

  public UserRole setId(Long id) {
    this.id = id;
    return this;
  }

  public UserRoleEnum getRole() {
    return role;
  }

  public UserRole setRole(UserRoleEnum role) {
    this.role = role;
    return this;
  }
}
