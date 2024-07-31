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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(
        username = "Pesho",
        roles = {"USER", "ADMIN"})
public class CarModelControllerIT {
    @Autowired
    private CarBrandRepository carBrandRepository;

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        carModelRepository.deleteAll();
        carBrandRepository.deleteAll();
    }

    @Test
    public void testGetAllCarModels() throws Exception {
        createTestCarModelList();

        mockMvc.perform(get("/carModel")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name", is("Sorento")))
                .andExpect(jsonPath("$.[1].name", is("Ceed")));
    }

    @Test
    public void testGetCarModelById() throws Exception {
        CarModel actualEntity = createTestModel();

        mockMvc.perform(get("/carModel/{id}", actualEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actualEntity.getId().intValue())))
                .andExpect(jsonPath("$.name", is(actualEntity.getName())))
                .andExpect(jsonPath("$.description", is(actualEntity.getDescription())));
    }

    @Test
    public void testGetModelByIdNotFound() throws Exception {
        mockMvc
                .perform(get("/carModel/{id}", "1000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateModel() throws Exception {
        CarBrand actualEntity = createTestBrand();
        MvcResult result = mockMvc.perform(post("/carModel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                    "name": "Sorento",
                                    "description": "description",
                                    "year": "2024-10-10",
                                    "brand": %d,
                                    "status": "true"
                                  }
                                """.formatted(actualEntity.getId()))
                ).andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();

        String message = JsonPath.read(body, "$.message");


        Optional<CarModel> carModelOptional = carModelRepository.findByName("Sorento");

        Assertions.assertTrue(carModelOptional.isPresent());

        CarModel carModel = carModelOptional.get();

        Assertions.assertEquals("Car model successfully created", message);
        Assertions.assertEquals("Sorento", carModel.getName());
        Assertions.assertEquals("description", carModel.getDescription());

    }

//    @Test
//    public void testUpdateModel() throws Exception {
//        CarModel actualEntity = createTestModel();
//        MvcResult result = mockMvc.perform(put("/carModel/{id}", actualEntity.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                                                  {
//                                                    "id":%d,
//                                                    "name": "Sorento",
//                                                    "description": "description22",
//                                                    "year": "2024-10-10",
//                                                    "status": "true",
//                                                    "brand": %d,
//                                                  }
//                                """.formatted(actualEntity.getId(), actualEntity.getBrand().getId()))
//                ).andExpect(status().isOk())
//                .andReturn();
//
//        String body = result.getResponse().getContentAsString();
//
//        int id = JsonPath.read(body, "$.id");
//
//        Optional<CarModel> carModelOptional = carModelRepository.findById((long) id);
//
//        Assertions.assertTrue(carModelOptional.isPresent());
//
//        CarModel user = carModelOptional.get();
//
//        Assertions.assertEquals("Sorento", user.getName());
//        Assertions.assertEquals("description22", user.getDescription());
//
//    }

    @Test
    public void testDeleteModel() throws Exception {

        CarModel actualEntity = createTestModel();

        mockMvc.perform(delete("/carModel/{id}", actualEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertTrue(carModelRepository.findById(actualEntity.getId()).isEmpty());
    }

    private CarModel createTestModel() {
        return carModelRepository.save(
                new CarModel("Sorento", "description", LocalDate.now(), createTestBrand(), true)
        );
    }

    private CarBrand createTestBrand() {
        CarBrand carBrand = new CarBrand("Kia", "description", "Kia OOD", List.of(), true);
        return carBrandRepository.save(carBrand);
    }

    private void createTestCarModelList() {
        CarBrand carBrand = createTestBrand();
        carModelRepository.save(
                new CarModel("Sorento", "description2", LocalDate.now(), carBrand, true)
        );
        carModelRepository.save(
                new CarModel("Ceed", "description21", LocalDate.now(), carBrand, true)
        );

    }
}
