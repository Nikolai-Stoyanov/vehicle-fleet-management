package my.project.vehiclefleetmanagement.model.dtos.carModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModelListDTO {
  private int id;
  private String name;
  private String description;
  private LocalDate year;
  private String brand;
}
