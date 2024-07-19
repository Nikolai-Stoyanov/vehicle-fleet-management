package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.model.entity.UserEntity;
import my.project.vehiclefleetmanagement.model.entity.UserRole;
import my.project.vehiclefleetmanagement.model.enums.UserRoleEnum;
import my.project.vehiclefleetmanagement.model.user.VfmUserDetails;
import my.project.vehiclefleetmanagement.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class VfmUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public VfmUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {

    return userRepository
        .findByEmail(email)
        .map(VfmUserDetailsService::map)
        .orElseThrow(
            () -> new UsernameNotFoundException("User with email " + email + " not found!"));
  }

  private static UserDetails map(UserEntity userEntity) {

    return new VfmUserDetails(
        userEntity.getEmail(),
        userEntity.getPassword(),
        userEntity.getRoles().stream().map(UserRole::getRole).map(VfmUserDetailsService::map).toList()
    );
  }

  private static GrantedAuthority map(UserRoleEnum role) {
    return new SimpleGrantedAuthority(
        "ROLE_" + role
    );
  }
}
