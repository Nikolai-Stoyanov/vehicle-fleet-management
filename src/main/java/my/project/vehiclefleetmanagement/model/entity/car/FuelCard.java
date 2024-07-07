package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import my.project.vehiclefleetmanagement.model.entity.BaseEntity;
@Entity
@Table(name = "fuel_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelCard extends BaseEntity {

    @Column(nullable = false, unique = true,name = "card_number")
    private String cardNumber;
    @Column(name = "money_limit")
    private String moneyLimit;
    @Column(nullable = false, name = "is_active")
    private boolean isActive;
}
