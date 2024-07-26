package my.project.vehiclefleetmanagement.repository;

import my.project.vehiclefleetmanagement.model.entity.declaration.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeclarationRepository extends JpaRepository<Declaration, Long> {
    Optional<Declaration> findById(long id);
}
