package my.project.vehiclefleetmanagement.repository.web.nomenclatures;

import com.jayway.jsonpath.JsonPath;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelEntity;
import my.project.vehiclefleetmanagement.repository.FuelRepository;
import my.project.vehiclefleetmanagement.repository.FuelSupplierRepository;
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
public class FuelControllerIT {
    @Autowired
    private FuelRepository fuelRepository;

    @Autowired
    private FuelSupplierRepository fuelSupplierRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {

        fuelSupplierRepository.deleteAll();
        fuelRepository.deleteAll();
    }


    @Test
    public void testGetAllFuels() throws Exception {
        createTestFuelList();

        mockMvc.perform(get("/fuel")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name", is("LPG")))
                .andExpect(jsonPath("$.[1].name", is("Diesel")));
    }

    @Test
    public void testGetFuelById() throws Exception {
        FuelEntity actualEntity = createTestFuel();

        mockMvc.perform(get("/fuel/{id}", actualEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actualEntity.getId().intValue())))
                .andExpect(jsonPath("$.name", is(actualEntity.getName())))
                .andExpect(jsonPath("$.description", is(actualEntity.getDescription())));
    }

    @Test
    public void testGetFuelByIdNotFound() throws Exception {
        mockMvc
                .perform(get("/fuel/{id}", "1000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateFuel() throws Exception {
        MvcResult result = mockMvc.perform(post("/fuel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {
                    "name": "Disel",
                    "description": "description",
                    "status": "true"
                  }
                """)
                ).andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();

        String message = JsonPath.read(body, "$.message");


        Optional<FuelEntity> optionalFuel = fuelRepository.findByName("Disel");

        Assertions.assertTrue(optionalFuel.isPresent());

        FuelEntity fuel = optionalFuel.get();

        Assertions.assertEquals("Fuel successfully created", message);
        Assertions.assertEquals("Disel", fuel.getName());
        Assertions.assertEquals("description", fuel.getDescription());

    }

//    @Test
//    public void testUpdateFuel() throws Exception {
//        FuelEntity actualEntity = createTestFuel();
//        MvcResult result = mockMvc.perform(put("/fuel/{id}",actualEntity.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                  {
//                    "id": %d
//                    "name": "Disel",
//                    "description": "description",
//                    "status": true
//                  }
//                """.formatted(actualEntity.getId()))
//                ).andExpect(status().isOk())
//                .andReturn();
//
//        String body = result.getResponse().getContentAsString();
//
//        String message = JsonPath.read(body, "$.message");
//
//
//        Optional<FuelEntity> optionalFuel = fuelRepository.findByName("Disel");
//
//        Assertions.assertTrue(optionalFuel.isPresent());
//
//        FuelEntity fuel = optionalFuel.get();
//
//        Assertions.assertEquals("Fuel successfully updated", message);
//        Assertions.assertEquals("Disel", fuel.getName());
//        Assertions.assertEquals("description", fuel.getDescription());
//
//    }

    @Test
    public void testDeleteFuel() throws Exception {

        FuelEntity actualEntity = createTestFuel();

        mockMvc.perform(delete("/fuel/{id}", actualEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertTrue(fuelRepository.findById(actualEntity.getId()).isEmpty());
    }

    private FuelEntity createTestFuel() {
        return fuelRepository.save(
                new FuelEntity("LPG", "description",true)
        );
    }

    private void createTestFuelList() {
        fuelRepository.save(
                new FuelEntity("SuperDiesel", "description1", true));
        fuelRepository.save(
                new FuelEntity("Diesel", "description8", true));

    }
}
