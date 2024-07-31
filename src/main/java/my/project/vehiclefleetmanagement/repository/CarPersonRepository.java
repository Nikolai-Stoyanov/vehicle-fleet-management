package my.project.vehiclefleetmanagement.repository;

import my.project.vehiclefleetmanagement.model.entity.car.CarPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarPersonRepository extends JpaRepository<CarPerson, Long> {
    Optional<CarPerson> findByPhoneNumber(String phoneNumber);

}
