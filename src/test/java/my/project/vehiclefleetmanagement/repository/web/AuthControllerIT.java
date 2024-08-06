package my.project.vehiclefleetmanagement.repository.web;

import my.project.vehiclefleetmanagement.model.entity.user.UserEntity;
import my.project.vehiclefleetmanagement.model.entity.user.UserRole;
import my.project.vehiclefleetmanagement.model.enums.UserRoleEnum;
import my.project.vehiclefleetmanagement.repository.UserRepository;
import my.project.vehiclefleetmanagement.repository.UserRolesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRolesRepository.save(
                new UserRole(1L, UserRoleEnum.ADMIN)
        );
        userRolesRepository.save(
                new UserRole(2L, UserRoleEnum.USER)
        );
    }

    @Test
    void testRegister() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                          {
                            "email": "Pesho@example.com",
                            "username": "Pesho",
                            "confirmPassword": 1234,
                            "password": "1234"
                          }
                        """)
        );

        Optional<UserEntity> userEntityOpt = userRepository.findByUsername("Pesho");
        Assertions.assertTrue(userEntityOpt.isPresent());
        UserEntity userEntity = userEntityOpt.get();
        Assertions.assertEquals("Pesho@example.com", userEntity.getEmail());
        Assertions.assertEquals("Pesho", userEntity.getUsername());
        Assertions.assertTrue(passwordEncoder.matches("1234", userEntity.getPassword()));
    }

    @Test
    void testLogin() throws Exception {}


}
