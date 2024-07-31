package my.project.vehiclefleetmanagement.repository;

import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuelRepository extends JpaRepository<FuelEntity, Long> {
    Optional<FuelEntity> findByName(String name);
}
