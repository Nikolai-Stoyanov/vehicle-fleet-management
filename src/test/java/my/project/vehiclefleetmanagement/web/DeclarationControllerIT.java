package my.project.vehiclefleetmanagement.web;


import my.project.vehiclefleetmanagement.model.entity.car.CarPerson;
import my.project.vehiclefleetmanagement.model.entity.car.CarRecord;
import my.project.vehiclefleetmanagement.model.entity.car.RegistrationCertificateData;
import my.project.vehiclefleetmanagement.model.entity.declaration.Declaration;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarBrand;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarModel;
import my.project.vehiclefleetmanagement.model.enums.DrivingCategoryType;
import my.project.vehiclefleetmanagement.model.enums.FuelType;
import my.project.vehiclefleetmanagement.model.enums.VehicleTypeEnum;
import my.project.vehiclefleetmanagement.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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
public class DeclarationControllerIT {
    @Autowired
    private DeclarationRepository declarationRepository;
    @Autowired
    private CarRecordRepository carRecordRepository;
    @Autowired
    private RegistrationCertificateDataRepository registrationCertificateDataRepository;
    @Autowired
    private CarBrandRepository carBrandRepository;
    @Autowired
    private CarModelRepository carModelRepository;
    @Autowired
    private CarPersonRepository carPersonRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
//        carBrandRepository.deleteAll();
//        carModelRepository.deleteAll();
//        carPersonRepository.deleteAll();
//        registrationCertificateDataRepository.deleteAll();
//        carRecordRepository.deleteAll();
        declarationRepository.deleteAll();
    }


    @Test
    public void testGetAllDeclarations() throws Exception {
        createDeclarationList();

        mockMvc.perform(get("/declaration")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].period", is("Aug.2024")))
                .andExpect(jsonPath("$.[1].period", is("Jul.2024")));
    }

    @Test
    public void testGetDeclarationById() throws Exception {
        Declaration actualEntity = createDeclaration();

        mockMvc.perform(get("/declaration/{id}", actualEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actualEntity.getId().intValue())))
                .andExpect(jsonPath("$.period", is(actualEntity.getPeriod())))
                .andExpect(jsonPath("$.date", is(actualEntity.getDate().toString())));
    }

    @Test
    public void testDeclarationByIdNotFound() throws Exception {
        mockMvc
                .perform(get("/declaration/{id}", "1000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void testCreateDeclaration() throws Exception {
//        CarRecord carRecord = createCarRecord();
//        MvcResult result = mockMvc.perform(post("/declaration")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                  {
//                    "carRecord":  %s,
//                    "date": "%s",
//                    "period": "Aug.2024",
//                    "lastMileage": 1500,
//                    "newMileage": 1500,
//                    "fuelKind": 1,
//                    "fuelSupplier": 1,
//                    "fuelAmount": 30,
//                    "fuelPrice": 3,
//                  }
//                """.formatted(carRecord.toString(),LocalDate.now()))
//                ).andExpect(status().isOk())
//                .andReturn();
//
//        String body = result.getResponse().getContentAsString();
//
//        String message = JsonPath.read(body, "$.message");
//
//
//        Optional<Declaration> optional = declarationRepository.findByPeriod("Aug.2024");
//
//        Assertions.assertTrue(optional.isPresent());
//
//        Declaration declaration = optional.get();
//
//        Assertions.assertEquals("Declaration successfully created!", message);
//        Assertions.assertEquals("Aug.2024", declaration.getPeriod());
//        Assertions.assertEquals(1500, declaration.getLastMileage());
//
//    }


    @Test
    public void testDeleteDeclaration() throws Exception {

        Declaration actualEntity = createDeclaration();

        mockMvc.perform(delete("/declaration/{id}", actualEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertTrue(declarationRepository.findById(actualEntity.getId()).isEmpty());
    }

    private Declaration createDeclaration() {
        return declarationRepository.save(
                new Declaration(createCarRecord(), LocalDate.now(), "Aug.2024", 1000, 1500,
                        1, 1, 30, 3, "User", LocalDate.now(), "", LocalDate.now()
                ));
    }

    private void createDeclarationList() {
        CarRecord carRecord =createCarRecord();
        declarationRepository.save(
                new Declaration(carRecord, LocalDate.now(), "Aug.2024", 1000, 1500,
                        1, 1, 30, 3, "User", LocalDate.now(), "", LocalDate.now()));
        declarationRepository.save(
                new Declaration(carRecord, LocalDate.now(), "Jul.2024", 1000, 1500,
                        1, 1, 30, 3, "User", LocalDate.now(), "", LocalDate.now()));

    }

    private CarRecord createCarRecord() {
        CarBrand carBrand = new CarBrand("Kia", "description", "Kia OOD", List.of(), true);
        Optional<CarBrand> optionalCarBrand = carBrandRepository.findByName("Kia");
        if (optionalCarBrand.isEmpty()){
            carBrandRepository.save(carBrand);
        }

        CarModel carModel = new CarModel("Sorento", "Kia", LocalDate.now(), carBrand, true);
        Optional<CarModel> optionalCarModel = carModelRepository.findByName("Sorento");
        if (optionalCarModel.isEmpty()){
            carModelRepository.save(carModel);
        }


        CarPerson driver = new CarPerson("Asen", "Ivanov", "0888995544", true, "Asen Ivanov");
        CarPerson responsible =  new CarPerson("Milen", "Vasilev", "0879885522", true, "Milen Vasilev");
        Optional<CarPerson> optionalDriver = carPersonRepository.findByPhoneNumber("0888995544");
        if (optionalDriver.isEmpty()){
            carPersonRepository.save(driver);
        }
        Optional<CarPerson> optionalResponsible = carPersonRepository.findByPhoneNumber("0879885522");
        if (optionalResponsible.isEmpty()){
            carPersonRepository.save(responsible);
        }

        RegistrationCertificateData data = new RegistrationCertificateData(
                "C0030CB", LocalDate.now(),carModel, "frame", "engine",
                2000, 150, 90, 5, "red", "blue",
                1, VehicleTypeEnum.CAR);
        Optional<RegistrationCertificateData> optionalData = registrationCertificateDataRepository.findByRegistrationNumber("C0030CB");
        if (optionalData.isEmpty()){
            registrationCertificateDataRepository.save(data);
        }
        Optional<RegistrationCertificateData> data1=registrationCertificateDataRepository.findByRegistrationNumber("C0030CB");

        CarRecord carRecord = new CarRecord(
                DrivingCategoryType.B, true, "description", data1.get(), FuelType.DIESEL, "555666",
                "User", LocalDate.now(), "", LocalDate.now(), 1000, 1,
                50, "Me", "My firm", "home", driver, responsible
        );
        Optional<CarRecord> optionalCarRecord = Optional.ofNullable(carRecordRepository.findByRegistrationCertificateData(data));
        if (optionalCarRecord.isEmpty()){
            carRecordRepository.save(carRecord);
        }
        CarRecord carRecord1 =carRecordRepository.findByRegistrationCertificateData(data1.get());
      return   carRecord1;
    }
}

