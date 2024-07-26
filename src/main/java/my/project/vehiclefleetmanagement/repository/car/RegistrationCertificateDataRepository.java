package my.project.vehiclefleetmanagement.repository.car;

import my.project.vehiclefleetmanagement.model.entity.car.RegistrationCertificateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationCertificateDataRepository extends JpaRepository<RegistrationCertificateData, Long> {

}
