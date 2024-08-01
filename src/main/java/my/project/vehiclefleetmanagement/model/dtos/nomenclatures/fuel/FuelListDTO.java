package my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelListDTO {
  private int id;
  private String name;
  private String description;
  private boolean status;
}
