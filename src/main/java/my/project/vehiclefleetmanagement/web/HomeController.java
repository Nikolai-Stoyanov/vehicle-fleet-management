package my.project.vehiclefleetmanagement.web;


import my.project.vehiclefleetmanagement.model.user.VfmUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeController {

  @GetMapping
  public String home(@AuthenticationPrincipal UserDetails userDetails,
      Model model) {

    if (userDetails instanceof VfmUserDetails vfmUserDetails) {
      model.addAttribute("welcomeMessage", vfmUserDetails.getUsername());
    } else {
      model.addAttribute("welcomeMessage", "Anonymous");
    }

    return "index";
  }
}
