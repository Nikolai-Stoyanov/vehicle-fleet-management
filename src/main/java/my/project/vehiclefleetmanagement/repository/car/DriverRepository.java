package my.project.vehiclefleetmanagement.repository.car;

import my.project.vehiclefleetmanagement.model.entity.car.CarPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DriverRepository extends JpaRepository<CarPerson, Long> {
    Optional<CarPerson> findByName(String name);

}
