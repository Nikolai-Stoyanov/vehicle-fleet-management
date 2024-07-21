package my.project.vehiclefleetmanagement.repository.car;

import my.project.vehiclefleetmanagement.model.entity.car.RegistrationNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationNumberRepository extends JpaRepository<RegistrationNumber, Long> {
    Optional<RegistrationNumber> findByRegistration(String name);

}
