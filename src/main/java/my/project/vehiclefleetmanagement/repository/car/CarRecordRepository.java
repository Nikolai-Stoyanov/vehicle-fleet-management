package my.project.vehiclefleetmanagement.repository.car;

import my.project.vehiclefleetmanagement.model.entity.car.CarRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRecordRepository extends JpaRepository<CarRecord, Long> {
    Optional<CarRecord> findById(long id);
}
