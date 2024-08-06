package my.project.vehiclefleetmanagement.web;

import com.jayway.jsonpath.JsonPath;
import my.project.vehiclefleetmanagement.model.entity.car.CarPerson;
import my.project.vehiclefleetmanagement.repository.CarPersonRepository;
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
public class CarPersonControllerIT {
    @Autowired
    private CarPersonRepository carPersonRepository;


    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        carPersonRepository.deleteAll();
    }


    @Test
    public void testGetAllCarPersons() throws Exception {
        createCarPersonList();

        mockMvc.perform(get("/carPerson")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].phoneNumber", is("0899112233")))
                .andExpect(jsonPath("$.[1].phoneNumber", is("0899115544")));
    }

    @Test
    public void testGetCarPersonById() throws Exception {
        CarPerson actualEntity = createCarPerson();

        mockMvc.perform(get("/carPerson/{id}", actualEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actualEntity.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(actualEntity.getFirstName())))
                .andExpect(jsonPath("$.phoneNumber", is(actualEntity.getPhoneNumber())));
    }

    @Test
    public void testGetCarPersonByIdNotFound() throws Exception {
        mockMvc
                .perform(get("/carPerson/{id}", "1000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateCarPerson() throws Exception {
        MvcResult result = mockMvc.perform(post("/carPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {
                    "firstName": "Georgi",
                    "lastName": "Petrov",
                    "phoneNumber": "0888585252",
                    "status": "true"
                  }
                """)
                ).andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();

        String message = JsonPath.read(body, "$.message");


        Optional<CarPerson> optional = carPersonRepository.findByPhoneNumber("0888585252");

        Assertions.assertTrue(optional.isPresent());

        CarPerson fuel = optional.get();

        Assertions.assertEquals("Person successfully created", message);
        Assertions.assertEquals("Petrov", fuel.getLastName());
        Assertions.assertEquals("0888585252", fuel.getPhoneNumber());

    }

//    @Test
//    public void testUpdateFuel() throws Exception {
//        FuelEntity actualEntity = createTestFuel();
//        MvcResult result = mockMvc.perform(put("/carPerson/{id}",actualEntity.getId())
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
    public void testDeleteCarPerson() throws Exception {

        CarPerson actualEntity = createCarPerson();

        mockMvc.perform(delete("/carPerson/{id}", actualEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertTrue(carPersonRepository.findById(actualEntity.getId()).isEmpty());
    }

    private CarPerson createCarPerson() {
        return carPersonRepository.save(
                new CarPerson("Ivan", "Petrov", "0899112233", true, "Ivan Petrov")
        );
    }

    private void createCarPersonList() {
        carPersonRepository.save(
                new CarPerson("Ivan", "Petrov", "0899112233", true, "Ivan Petrov"));
        carPersonRepository.save(
                new CarPerson("Petar", "Ivanov", "0899115544", true, "Petar Ivanov"));

    }
}
