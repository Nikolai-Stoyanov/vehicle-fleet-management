package my.project.vehiclefleetmanagement.repository;

import my.project.vehiclefleetmanagement.model.entity.user.UserEntity;
import my.project.vehiclefleetmanagement.model.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRole, Long> {


}