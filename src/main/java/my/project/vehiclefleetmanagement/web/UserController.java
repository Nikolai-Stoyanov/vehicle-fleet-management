package my.project.vehiclefleetmanagement.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.project.vehiclefleetmanagement.model.dtos.user.*;
import my.project.vehiclefleetmanagement.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserListDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserEditDTO userEditDTO) {
        userService.updateUser(id, userEditDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserByIdDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles")
    public ResponseEntity<List<UserRoleDto>> getAllRoles() {
        return ResponseEntity.ok(userService.getAllRoles());
    }
}
