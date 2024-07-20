package my.project.vehiclefleetmanagement.web.user;

import my.project.vehiclefleetmanagement.model.dtos.user.UserLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

  @GetMapping
  public String login() {
    return "auth-login";
  }

  @PostMapping
  public ResponseEntity<UserLoginDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
    return ResponseEntity.ok().body(userLoginDTO);
  }
}
