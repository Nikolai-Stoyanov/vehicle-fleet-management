package my.project.vehiclefleetmanagement.repository;

import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {
    Optional<CarBrand> findByName(String name);

    @Query(value = "FROM CarBrand c WHERE c.name LIKE %:filter% OR c.description LIKE %:filter% " +
            "OR c.company LIKE %:filter%")
    List<CarBrand> findAll(String filter);
}
