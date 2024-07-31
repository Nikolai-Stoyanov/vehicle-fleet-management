package my.project.vehiclefleetmanagement.web;

import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationDTO;
import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationListDTO;
import my.project.vehiclefleetmanagement.service.DeclarationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/declaration")

public class DeclarationController {

    private final DeclarationService declarationService;

    public DeclarationController(DeclarationService declarationService) {
        this.declarationService = declarationService;
    }

    @GetMapping
    public ResponseEntity<List<DeclarationListDTO>> getAllDeclarations() {
        return ResponseEntity.ok(
                declarationService.getAllDeclarations()
        );
    }

    @PostMapping
    public ResponseEntity<String> createDeclaration(@RequestBody DeclarationCreateDTO declarationCreateDTO) {
        declarationService.createDeclaration(declarationCreateDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDeclaration(@PathVariable("id") Long id,@RequestBody DeclarationEditDTO declarationEditDTO) {
        declarationService.updateDeclaration(id,declarationEditDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeclarationDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(declarationService.getDeclarationById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        declarationService.deleteDeclaration(id);
        return ResponseEntity.noContent().build();
    }
}
