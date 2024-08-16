package my.project.vehiclefleetmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class VehicleFleetManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleFleetManagementApplication.class, args);
    }

}
