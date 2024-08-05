package my.project.vehiclefleetmanagement.repository.web.nomenclatures;

import com.jayway.jsonpath.JsonPath;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarBrand;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarModel;
import my.project.vehiclefleetmanagement.repository.CarBrandRepository;
import my.project.vehiclefleetmanagement.repository.CarModelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(
        username = "Pesho",
        roles = {"USER", "ADMIN"})
public class CarBrandControllerIT {
    @Autowired
    private CarBrandRepository carBrandRepository;

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        carBrandRepository.deleteAll();
    }

    @Test
    public void testGetAllBrands() throws Exception {
        createTestCarBrandList();

        mockMvc.perform(get("/carBrand")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name", is("Kia")))
                .andExpect(jsonPath("$.[1].name", is("Opel")));
    }

    @Test
    public void testGetCarBrandById() throws Exception {
        CarBrand actualEntity = createTestBrand();

        mockMvc.perform(get("/carBrand/{id}", actualEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actualEntity.getId().intValue())))
                .andExpect(jsonPath("$.name", is(actualEntity.getName())))
                .andExpect(jsonPath("$.description", is(actualEntity.getDescription())))
                .andExpect(jsonPath("$.company", is(actualEntity.getCompany())));
    }

    @Test
    public void testGetBrandByIdNotFound() throws Exception {
        mockMvc
                .perform(get("/carBrand/{id}", "1000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateBrand() throws Exception {
        MvcResult result = mockMvc.perform(post("/carBrand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {
                    "name": "Kia",
                    "description": "description",
                    "company": "Kia OOD",
                    "status": "true"
                  }
                """)
                ).andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();

        String message = JsonPath.read(body, "$.message");


        Optional<CarBrand> carBrandOptional = carBrandRepository.findByName("Kia");

        Assertions.assertTrue(carBrandOptional.isPresent());

        CarBrand carBrand = carBrandOptional.get();

        Assertions.assertEquals("Car brand successfully created", message);
        Assertions.assertEquals("Kia", carBrand.getName());
        Assertions.assertEquals("description", carBrand.getDescription());
        Assertions.assertEquals("Kia OOD", carBrand.getCompany());

    }

//    @Test
//    public void testUpdateBrand() throws Exception {
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
    public void testDeleteBrand() throws Exception {

        CarBrand actualEntity = createTestBrand();

        mockMvc.perform(delete("/carBrand/{id}", actualEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertTrue(carBrandRepository.findById(actualEntity.getId()).isEmpty());
    }

    private CarBrand createTestBrand() {
        CarBrand carBrand = new CarBrand("Kia1", "description", "Kia OOD", List.of(),true);
        return carBrandRepository.save(carBrand);
    }

    private void createTestCarBrandList() {
        carBrandRepository.save(
                new CarBrand("Kia1", "description", "Kia OOD", List.of(),true));
        carBrandRepository.save(
                new CarBrand("Opel", "description", "Opel OOD", List.of(),true));

    }
}
