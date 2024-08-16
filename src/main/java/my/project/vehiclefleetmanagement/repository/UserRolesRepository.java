package my.project.vehiclefleetmanagement.repository;

import my.project.vehiclefleetmanagement.model.entity.user.UserRole;
import my.project.vehiclefleetmanagement.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRole, Long> {


    UserRole findByRole(UserRoleEnum role);
    List<UserRole> findAll();
}