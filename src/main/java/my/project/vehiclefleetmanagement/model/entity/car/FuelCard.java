package my.project.vehiclefleetmanagement.model.entity.car;

import jakarta.persistence.*;
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
    @Column(nullable = false)
    private boolean status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "carRecord_id")
    private CarRecord carRecord;
}
