package my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.BrandDTO;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModelDTO {
  private int id;
  private String name;
  private String description;
  private String year;
  private BrandDTO brand;
  private boolean status;
}
