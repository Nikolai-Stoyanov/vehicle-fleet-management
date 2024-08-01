package my.project.vehiclefleetmanagement.repository;

import my.project.vehiclefleetmanagement.model.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String name);

    Optional<UserEntity>  findByUsernameOrEmail(String username, String email);

    Optional<UserEntity> findByEmail(String email);
}