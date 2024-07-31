package my.project.vehiclefleetmanagement.model.dtos.declaration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationListDTO {
  private long id;
  private String period;
  private String date;
  private String responsible;
  private String driver;
  private String registrationNumber;
}
