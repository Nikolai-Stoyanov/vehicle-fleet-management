package my.project.vehiclefleetmanagement.web;

import com.jayway.jsonpath.JsonPath;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelSupplier;
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
public class FuelSupplierControllerIT {
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
    public void testGetAllSuppliers() throws Exception {
        createTestSupplierList();

        mockMvc.perform(get("/fuelSupplier")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name", is("OMV")))
                .andExpect(jsonPath("$.[1].name", is("Petrol")));
    }

    @Test
    public void testGetSupplierById() throws Exception {
        FuelSupplier actualEntity = createTestSupplier();

        mockMvc.perform(get("/fuelSupplier/{id}", actualEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actualEntity.getId().intValue())))
                .andExpect(jsonPath("$.name", is(actualEntity.getName())))
                .andExpect(jsonPath("$.description", is(actualEntity.getDescription())));
    }

    @Test
    public void testGetSupplierByIdNotFound() throws Exception {
        mockMvc
                .perform(get("/fuelSupplier/{id}", "1000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateSupplier() throws Exception {
        MvcResult result = mockMvc.perform(post("/fuelSupplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                    "name": "OMV",
                                    "description": "description",
                                    "fuelList": [],
                                    "status": "true"
                                  }
                                """)
                ).andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();

        String message = JsonPath.read(body, "$.message");

        Optional<FuelSupplier> optionalFuelSupplier = fuelSupplierRepository.findByName("OMV");

        Assertions.assertTrue(optionalFuelSupplier.isPresent());

        FuelSupplier fuel = optionalFuelSupplier.get();

        Assertions.assertEquals("Fuel supplier successfully created", message);
        Assertions.assertEquals("OMV", fuel.getName());
        Assertions.assertEquals("description", fuel.getDescription());

    }

//    @Test
//    public void testUpdateSupplier() throws Exception {
//        FuelSupplier actualEntity = createTestSupplier();
//        MvcResult result = mockMvc.perform(put("/fuelSupplier/{id}",actualEntity.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                  {
//                    "id": %d
//                    "name": "OMV",
//                    "description": "description2",
//                    "fuelList": [],
//                    "status": "true"
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
//        Optional<FuelSupplier> optionalFuelSupplier = fuelSupplierRepository.findByName("OMV");
//
//        Assertions.assertTrue(optionalFuelSupplier.isPresent());
//
//        FuelSupplier fuel = optionalFuelSupplier.get();
//
//        Assertions.assertEquals("Fuel successfully updated", message);
//        Assertions.assertEquals("OMV", fuel.getName());
//        Assertions.assertEquals("description2", fuel.getDescription());
//
//    }

    @Test
    public void testDeleteSupplier() throws Exception {

        FuelSupplier actualEntity = createTestSupplier();

        mockMvc.perform(delete("/fuelSupplier/{id}", actualEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertTrue(fuelRepository.findById(actualEntity.getId()).isEmpty());
    }

    private FuelSupplier createTestSupplier() {
        return fuelSupplierRepository.save(
                new FuelSupplier("OMV", "description1", List.of(), true)
        );
    }

    private void createTestSupplierList() {
        fuelSupplierRepository.save(
                new FuelSupplier("OMV", "description1", List.of(), true));
        fuelSupplierRepository.save(
                new FuelSupplier("Petrol", "description8", List.of(), true));

    }
}
