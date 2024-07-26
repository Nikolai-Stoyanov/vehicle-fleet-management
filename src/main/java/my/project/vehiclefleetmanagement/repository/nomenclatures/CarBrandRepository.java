package my.project.vehiclefleetmanagement.repository.nomenclatures;

import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {
    Optional<CarBrand> findByName(String name);
}
