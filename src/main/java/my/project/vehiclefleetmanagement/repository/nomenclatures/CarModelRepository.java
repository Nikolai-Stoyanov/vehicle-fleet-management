package my.project.vehiclefleetmanagement.repository.nomenclatures;

import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    Optional<CarModel> findByName(String name);
}
