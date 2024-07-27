package my.project.vehiclefleetmanagement.web;

import com.jayway.jsonpath.JsonPath;
import my.project.vehiclefleetmanagement.model.entity.user.UserEntity;
import my.project.vehiclefleetmanagement.model.entity.user.UserRole;
import my.project.vehiclefleetmanagement.model.enums.UserRoleEnum;
import my.project.vehiclefleetmanagement.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(
        username = "Pesho",
        roles = {"USER", "ADMIN"})
public class UserControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetUserById() throws Exception {
        UserEntity actualEntity = createTestUser();
        mockMvc
                .perform(get("/users/{id}", actualEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actualEntity.getId().intValue())))
                .andExpect(jsonPath("$.email", is(actualEntity.getEmail())))
                .andExpect(jsonPath("$.username", is(actualEntity.getUsername())));
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        mockMvc
                .perform(get("/users/{id}", "1000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


//    @Test
//    public void testUpdateUser() throws Exception {
//        UserEntity actualEntity = createTestUser();
//        MvcResult result = mockMvc.perform(put("/users/{id}",1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                  {
//
//                    "email": "gosho@abv.bg",
//                    "username": "Gosho",
//                    "roles": []
//                  }
//                """)
//                ).andExpect(status().isOk())
//                .andReturn();
//
//        String body = result.getResponse().getContentAsString();
//
//        int id = JsonPath.read(body, "$.id");
//
//        Optional<UserEntity> userEntityOptional = userRepository.findById((long) id);
//
//        Assertions.assertTrue(userEntityOptional.isPresent());
//
//        UserEntity user = userEntityOptional.get();
//
//        Assertions.assertEquals("gosho@abv.bg", user.getEmail());
//        Assertions.assertEquals("Gosho", user.getUsername());
//
//    }

    @Test
    public void testDeleteUser() throws Exception {

        UserEntity actualEntity = createTestUser();

        mockMvc.perform(delete("/users/{id}", actualEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertTrue(userRepository.findById(actualEntity.getId()).isEmpty());
    }

    private UserEntity createTestUser() {
        return userRepository.save(
                new UserEntity("gosho@abv.bg", "1234", "Gosho",
                        List.of(new UserRole(1L, UserRoleEnum.ADMIN)))
        );
    }
}
