package my.project.vehiclefleetmanagement.service.declaration;


import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationDTO;
import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationListDTO;

import java.util.List;

public interface DeclarationService {

    boolean createDeclaration(DeclarationCreateDTO declarationCreateDTO);

    List<DeclarationListDTO> getAllDeclarations();

    DeclarationDTO getDeclarationById(Long id);

    void deleteDeclaration(Long id);

    boolean updateDeclaration(Long id, DeclarationEditDTO declarationEditDTO);
}
