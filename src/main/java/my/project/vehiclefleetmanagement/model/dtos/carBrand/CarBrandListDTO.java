package my.project.vehiclefleetmanagement.model.dtos.carBrand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarBrandListDTO {
  private int id;
  private String name;
  private String description;
  private String company;
  private List<String> models;
}
