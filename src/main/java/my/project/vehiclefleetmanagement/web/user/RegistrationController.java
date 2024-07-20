package my.project.vehiclefleetmanagement.web.user;

import my.project.vehiclefleetmanagement.model.dtos.user.UserRegistrationDTO;
import my.project.vehiclefleetmanagement.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")

public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO registerDTO) {

        boolean success = userService.registerUser(registerDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User already exist!");
        }
    }
}
