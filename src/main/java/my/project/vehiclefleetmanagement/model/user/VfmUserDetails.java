package my.project.vehiclefleetmanagement.model.user;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class VfmUserDetails extends User {

  public VfmUserDetails(
      String username,
      String password,
      Collection<? extends GrantedAuthority> authorities
  ) {
    super(username, password, authorities);
  }

}
