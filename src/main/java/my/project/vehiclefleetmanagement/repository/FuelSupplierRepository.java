package my.project.vehiclefleetmanagement.repository;

import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuelSupplierRepository extends JpaRepository<FuelSupplier, Long> {
    Optional<FuelSupplier> findByName(String name);
}
