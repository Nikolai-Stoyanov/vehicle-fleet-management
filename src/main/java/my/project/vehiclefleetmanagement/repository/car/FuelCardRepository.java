package my.project.vehiclefleetmanagement.repository.car;

import my.project.vehiclefleetmanagement.model.entity.car.FuelCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuelCardRepository extends JpaRepository<FuelCard, Long> {
    Optional<FuelCard> findByCardNumber(String cardNumber);

}
