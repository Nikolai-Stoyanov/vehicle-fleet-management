package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationDTO;
import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.declaration.DeclarationListDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierDTO;
import my.project.vehiclefleetmanagement.model.entity.car.CarPerson;
import my.project.vehiclefleetmanagement.model.entity.car.CarRecord;
import my.project.vehiclefleetmanagement.model.entity.car.RegistrationCertificateData;
import my.project.vehiclefleetmanagement.model.entity.declaration.Declaration;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarModel;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelEntity;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelSupplier;
import my.project.vehiclefleetmanagement.model.enums.DrivingCategoryType;
import my.project.vehiclefleetmanagement.model.enums.FuelType;
import my.project.vehiclefleetmanagement.model.enums.VehicleTypeEnum;
import my.project.vehiclefleetmanagement.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeclarationServiceImplTest {

    private DeclarationServiceImpl toTest;

    @Captor
    private ArgumentCaptor<Declaration> declarationArgumentCaptor;

    @Mock
    private DeclarationRepository mockDeclarationRepository;
    @Mock
    private FuelRepository mockFuelRepository;
    @Mock
    private FuelSupplierRepository mockFuelSupplierRepository;
    @Mock
    private CarRecordRepository mockCarRecordRepository;
    @Mock
    private RegistrationCertificateDataRepository mockRegDataRepository;


    @BeforeEach
    void setUp() {
        toTest = new DeclarationServiceImpl(
                mockRegDataRepository,
                mockFuelRepository,
                mockFuelSupplierRepository,
                mockDeclarationRepository,
                new ModelMapper(),
                mockCarRecordRepository
        );

    }

    @Test
    @Disabled
    void testCreateDeclaration() {
        RegistrationCertificateData data = new RegistrationCertificateData(
                "C0030CB",
                LocalDate.now(),
                new CarModel(),
                "frame",
                "engine",
                2000,
                150,
                90,
                5,
                "red",
                "blue",
                1,
                VehicleTypeEnum.CAR);

        CarRecord carRecord = new CarRecord(
                DrivingCategoryType.B,
                true,
                "description",
                data,
                FuelType.DIESEL,
                "555666",
                "User",
                LocalDate.now(),
                "",
                LocalDate.now(),
                1000,
                1,
                50,
                "Me",
                "My firm",
                "home",
                new CarPerson(),
                new CarPerson()
        );

        DeclarationCreateDTO declarationCreateDTO = new DeclarationCreateDTO(
                "C0030CB",
                "Aug.2024",
                "2024-08-01",
                1000,
                1500,
                1,
                1,
                30,
                3
        );

        when(mockRegDataRepository.findByRegistrationNumber(declarationCreateDTO.getRegistrationNumber()))
                .thenReturn(Optional.of(data));
        when(mockCarRecordRepository.findByRegistrationCertificateData(data))
                .thenReturn(carRecord);
        when(mockDeclarationRepository.findByPeriodAndCarRecord(declarationCreateDTO.getPeriod(), carRecord))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createDeclaration(declarationCreateDTO)
        );

        String expectedMessage = "Declaration successfully created!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockDeclarationRepository).save(declarationArgumentCaptor.capture());
        Declaration actualSavedEntity = declarationArgumentCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(declarationCreateDTO.getPeriod(), actualSavedEntity.getPeriod());
        assertEquals(LocalDate.parse(declarationCreateDTO.getDate()), actualSavedEntity.getDate());
        assertEquals(declarationCreateDTO.getNewMileage(), actualSavedEntity.getNewMileage());
    }

    @Test
    void testCreateDeclarationFailedDeclarationExist() {
        RegistrationCertificateData data = new RegistrationCertificateData(
                "C0030CB",
                LocalDate.now(),
                new CarModel(),
                "frame",
                "engine",
                2000,
                150,
                90,
                5,
                "red",
                "blue",
                1,
                VehicleTypeEnum.CAR);

        CarRecord carRecord = new CarRecord(
                DrivingCategoryType.B,
                true,
                "description",
                data,
                FuelType.DIESEL,
                "555666",
                "User",
                LocalDate.now(),
                "",
                LocalDate.now(),
                1000,
                1,
                50,
                "Me",
                "My firm",
                "home",
                new CarPerson(),
                new CarPerson()
        );

        DeclarationCreateDTO declarationCreateDTO = new DeclarationCreateDTO(
                "C0030CB",
                "Aug.2024",
                "2024-08-01",
                1000,
                1500,
                1,
                1,
                30,
                3
        );

        Declaration declaration = new Declaration(carRecord, LocalDate.now(), "Aug.2024", 1000,
                1500, 1, 1, 30, 3, "User", LocalDate.now(),
                "", LocalDate.now()
        );

        when(mockRegDataRepository.findByRegistrationNumber(declarationCreateDTO.getRegistrationNumber()))
                .thenReturn(Optional.of(data));
        when(mockCarRecordRepository.findByRegistrationCertificateData(data))
                .thenReturn(carRecord);
        when(mockDeclarationRepository.findByPeriodAndCarRecord(declarationCreateDTO.getPeriod(), carRecord))
                .thenReturn(Optional.ofNullable(declaration));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createDeclaration(declarationCreateDTO)
        );

        String expectedMessage = "Declaration for registration number C0030CB and period Aug.2024 is already exists!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreateDeclarationFailedRegistrationNumberNotFound() {
        DeclarationCreateDTO declarationCreateDTO = new DeclarationCreateDTO(
                "C0030CB",
                "Aug.2024",
                "2024-08-01",
                1000,
                1500,
                1,
                1,
                30,
                3
        );

        when(mockRegDataRepository.findByRegistrationNumber(declarationCreateDTO.getRegistrationNumber()))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createDeclaration(declarationCreateDTO)
        );

        String expectedMessage = "Car record with registration number C0030CB is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllDeclarations() {
        RegistrationCertificateData data = new RegistrationCertificateData(
                "C0030CB", LocalDate.now(), new CarModel(), "frame", "engine",
                2000, 150, 90, 5, "red", "blue",
                1, VehicleTypeEnum.CAR);

        CarRecord carRecord = new CarRecord(
                DrivingCategoryType.B, true, "description", data, FuelType.DIESEL, "555666",
                "User", LocalDate.now(), "", LocalDate.now(), 1000, 1,
                50, "Me", "My firm", "home",
                new CarPerson("Asen","Ivanov","0888995544",true,"Asen Ivanov"),
                new CarPerson("Milen","Vasilev","0879885522",true,"Milen Vasilev")
        );
        List<DeclarationListDTO> declarationListDTOS =
                List.of(
                        new DeclarationListDTO(1, "Aug.2024", "2024-08-01","Asen Ivanov","Milen Vasilev","C0030CB"),
                        new DeclarationListDTO(2, "Jul.2024", "2024-08-01","Asen Ivanov","Milen Vasilev","B0030CB" ));

        List<Declaration> declarations =
                List.of(
                        new Declaration(carRecord, LocalDate.now(), "Aug.2024", 1000, 1500,
                                1, 1, 30, 3, "User", LocalDate.now(), "", LocalDate.now()),
                        new Declaration(carRecord, LocalDate.now(), "Jul.2024", 1000, 1500,
                                1, 1, 30, 3, "User", LocalDate.now(), "", LocalDate.now()));

        when(mockCarRecordRepository.findById(declarations.get(0).getCarRecord().getId()))
                .thenReturn(Optional.of(carRecord));
        when(mockDeclarationRepository.findAll()).thenReturn(declarations);

        List<DeclarationListDTO>  result= toTest.getAllDeclarations();


        Assertions.assertEquals(declarationListDTOS.toArray().length, result.toArray().length);
        Assertions.assertEquals(declarationListDTOS.get(0).getDate(), result.get(0).getDate());
        Assertions.assertEquals(declarationListDTOS.get(1).getPeriod(), result.get(1).getPeriod());
        Assertions.assertEquals(declarationListDTOS.get(1).getDriver(), result.get(1).getDriver());
        Assertions.assertEquals(declarationListDTOS.get(1).getResponsible(), result.get(1).getResponsible());
    }

    @Test

    void testGetDeclarationById() {
        RegistrationCertificateData data = new RegistrationCertificateData(
                "C0030CB", LocalDate.now(), new CarModel(), "frame", "engine",
                2000, 150, 90, 5, "red", "blue",
                1, VehicleTypeEnum.CAR);

        CarRecord carRecord = new CarRecord(
                DrivingCategoryType.B, true, "description", data, FuelType.DIESEL, "555666",
                "User", LocalDate.now(), "", LocalDate.now(), 1000, 1,
                50, "Me", "My firm", "home", new CarPerson(), new CarPerson()
        );
        Declaration declaration = new Declaration(carRecord, LocalDate.now(), "Aug.2024", 1000,
                1500, 1, 1, 30, 3, "User", LocalDate.now(),
                "", LocalDate.now()
        );

        FuelEntity fuelEntity = new FuelEntity("LPG", "lpg", true);
        FuelSupplier fuelSupplier = new FuelSupplier("OMV", "", List.of(new FuelEntity()), true);

        DeclarationDTO declarationDTO =
                new DeclarationDTO(1, "Aug.2024", "2024-08-01", 1000, 1500,
                        "Petar Petrov","C0030CB","DIESEL", new FuelDTO(), new FuelSupplierDTO(),
                        30, 3,"User", "", "", "");

        Long id = 1L;

        when(mockDeclarationRepository.findById(id))
                .thenReturn(Optional.of(declaration));
        when(mockFuelRepository.findById(id))
                .thenReturn(Optional.of(fuelEntity));
        when(mockFuelSupplierRepository.findById(id))
                .thenReturn(Optional.of(fuelSupplier));

        DeclarationDTO result=  toTest.getDeclarationById(id);

        assertEquals(declarationDTO.getPeriod(), result.getPeriod());
        assertEquals(declarationDTO.getDate(), result.getDate());
        assertEquals(declarationDTO.getRegistrationNumber(), result.getRegistrationNumber());
    }

    @Test
    void testGetDeclarationByIdFailedDeclarationNotFound() {
        Long id = 1L;

        when(mockDeclarationRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.getDeclarationById(id)
        );

        String expectedMessage = "Declaration is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeletePerson() {
        Declaration declaration = new Declaration(new CarRecord(), LocalDate.now(), "Aug.2024", 1000,
                1500, 1, 1, 30, 3, "User", LocalDate.now(),
                "", LocalDate.now()
        );
        Long id = 1L;

        when(mockDeclarationRepository.findById(id))
                .thenReturn(Optional.of(declaration));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteDeclaration(id)
        );

        String expectedMessage = "Declaration successfully deleted!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteDeclarationFailedDeclarationNotFound() {
        Long id = 1L;
        when(mockDeclarationRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteDeclaration(id)
        );

        String expectedMessage = "Declaration is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Disabled
    void testUpdateDeclaration() {
        CarRecord carRecord = new CarRecord(
                DrivingCategoryType.B, true, "description", new RegistrationCertificateData(), FuelType.DIESEL, "555666",
                "User", LocalDate.now(), "", LocalDate.now(), 1000, 1,
                50, "Me", "My firm", "home", new CarPerson(), new CarPerson()
        );
        Declaration declaration = new Declaration(carRecord, LocalDate.now(), "Aug.2024", 1000,
                1500, 1, 1, 30, 3, "User", LocalDate.now(),
                "", LocalDate.now()
        );

        DeclarationEditDTO declarationEditDTO =
                new DeclarationEditDTO(1,"C0030CB", "Aug.2024", "2024-08-01",
                        1000, 1500, 1, 1, 30, 3,
                        "User", "", "", "");
        Long id = 1L;
        when(mockDeclarationRepository.findById(id))
                .thenReturn(Optional.of(declaration));
        Mockito.lenient().when(mockDeclarationRepository.findByPeriodAndCarRecord(declarationEditDTO.getPeriod(),declaration.getCarRecord()))
                .thenReturn(Optional.ofNullable(null));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateDeclaration(id, declarationEditDTO)
        );

        String expectedMessage = "Declaration successfully updated!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockDeclarationRepository).save(declarationArgumentCaptor.capture());
        Declaration actualSavedEntity = declarationArgumentCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(declarationEditDTO.getPeriod(), actualSavedEntity.getPeriod());
        assertEquals(LocalDate.parse(declarationEditDTO.getDate()), actualSavedEntity.getDate());
        assertEquals(declarationEditDTO.getCreatedBy(), actualSavedEntity.getCreatedBy());
    }

    @Test
    void testUpdateDeclarationFailedDeclarationNotFound() {

        DeclarationEditDTO declarationEditDTO =
                new DeclarationEditDTO(1,"C0030CB", "Aug.2024", "2024-08-01",
                        1000, 1500, 1, 1, 30, 3,
                        "User", "", "", "");
        Long id = 1L;

        when(mockDeclarationRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateDeclaration(id, declarationEditDTO)
        );

        String expectedMessage = "Declaration is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateDeclarationFailedPeriodAndRegistrationNumberExist() {

        CarRecord carRecord = new CarRecord(
                DrivingCategoryType.B, true, "description",new RegistrationCertificateData(), FuelType.DIESEL, "555666",
                "User", LocalDate.now(), "", LocalDate.now(), 1000, 1,
                50, "Me", "My firm", "home", new CarPerson(), new CarPerson()
        );
        Declaration declaration1 = new Declaration(carRecord, LocalDate.now(), "Aug.2024", 1000,
                1500, 1, 1, 30, 3, "User", LocalDate.now(),
                "", LocalDate.now()
        );

        Declaration declaration2 = new Declaration(carRecord, LocalDate.now(), "Jul.2024", 1000,
                1500, 1, 1, 30, 3, "User", LocalDate.now(),
                "", LocalDate.now()
        );

        DeclarationEditDTO declarationEditDTO =
                new DeclarationEditDTO(1,"C0030CB", "Jul.2024", "2024-08-01",
                        1000, 1500, 1, 1, 30, 3,
                        "User", "", "", "");
        Long id = 1L;

        when(mockDeclarationRepository.findById(id))
                .thenReturn(Optional.of(declaration1));
        Mockito.lenient().when(mockDeclarationRepository.findByPeriodAndCarRecord(declarationEditDTO.getPeriod(),declaration1.getCarRecord()))
                .thenReturn(Optional.of(declaration2));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateDeclaration(id, declarationEditDTO)
        );

        String expectedMessage = "Declaration for registration number C0030CB and period Jul.2024 is already exists!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}