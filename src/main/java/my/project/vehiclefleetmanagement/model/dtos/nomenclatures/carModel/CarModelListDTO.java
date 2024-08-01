package my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModelListDTO {
  private int id;
  private String name;
  private String description;
  private String year;
  private String brandName;
  private boolean status;
}
