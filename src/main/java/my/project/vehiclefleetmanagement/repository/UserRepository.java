package my.project.vehiclefleetmanagement.repository;

import my.project.vehiclefleetmanagement.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByUsernameOrEmail(String username, String email);
}