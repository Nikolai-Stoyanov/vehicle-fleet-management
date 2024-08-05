package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.car.*;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelListDTO;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarRecordServiceImplTest {

    private CarRecordServiceImpl toTest;

    @Captor
    private ArgumentCaptor<CarRecord> carRecordArgumentCaptor;

    @Mock
    private DeclarationRepository mockDeclarationRepository;
    @Mock
    private CarRecordRepository mockCarRecordRepository;
    @Mock
    private RegistrationCertificateDataRepository mockRegDataRepository;


    @BeforeEach
    void setUp() {
        toTest = new CarRecordServiceImpl(
                mockRegDataRepository,
                mockCarRecordRepository,
                mockDeclarationRepository,
                new ModelMapper()
        );

    }

    @Test
    @Disabled
    void testCreateCarRecord() {
        RegistrationCertificateData data =
                new RegistrationCertificateData("C0030CB", LocalDate.now(), new CarModel(), "frame",
                "engine", 2000, 150, 90, 5, "red",
                "blue", 1, VehicleTypeEnum.CAR);

        RegistrationCertificateDataDTO dataDTO =
                new RegistrationCertificateDataDTO("C0030CB", "2024-01-01",
                        new CarModelListDTO(), "frame", "engine", 2000, 150,
                        90, 5, "red", "blue", 1, "CAR");

        CarRecordCreateDTO carRecordCreateDTO =
                new CarRecordCreateDTO("B", "description",1000, 1,
                        50, "555666",  "DIESEL",dataDTO,true, "Me",
                        "My firm", "home", new CarPersonDTO(), new CarPersonDTO()

        );


        when(mockRegDataRepository.findByRegistrationNumber(carRecordCreateDTO.getRegistrationCertificateData().getRegistrationNumber()))
                .thenReturn(Optional.ofNullable(null))
                .thenReturn(Optional.of(data));

        //TODO get currentUser
        when(Mockito.mock(toTest.getCurrentUserName()))
                .thenReturn("testuser1");

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createCarRecord(carRecordCreateDTO)
        );

        String expectedMessage = "Car record successfully created!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockCarRecordRepository).save(carRecordArgumentCaptor.capture());
        CarRecord actualSavedEntity = carRecordArgumentCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(DrivingCategoryType.valueOf(carRecordCreateDTO.getDrivingCategory()), actualSavedEntity.getDrivingCategory());
        assertEquals(carRecordCreateDTO.getDescription(), actualSavedEntity.getDescription());
        assertEquals(FuelType.valueOf(carRecordCreateDTO.getFuelType()), actualSavedEntity.getFuelType());
        assertEquals(carRecordCreateDTO.getOwner(), actualSavedEntity.getOwner());
    }

    @Test
    void testCreateCarRecordFailedCarRecordExist() {
        RegistrationCertificateData data =
                new RegistrationCertificateData("C0030CB", LocalDate.now(), new CarModel(), "frame",
                        "engine", 2000, 150, 90, 5, "red",
                        "blue", 1, VehicleTypeEnum.CAR);
        RegistrationCertificateDataDTO dataDTO =
                new RegistrationCertificateDataDTO("C0030CB", "2024-01-01",
                        new CarModelListDTO(), "frame", "engine", 2000, 150,
                        90, 5, "red", "blue", 1, "CAR");

        CarRecordCreateDTO carRecordCreateDTO =
                new CarRecordCreateDTO("B", "description",1000, 1,
                        50, "555666",  "DIESEL",dataDTO,true, "Me",
                        "My firm", "home", new CarPersonDTO(), new CarPersonDTO()

                );

        when(mockRegDataRepository.findByRegistrationNumber(carRecordCreateDTO.getRegistrationCertificateData().getRegistrationNumber()))
                .thenReturn(Optional.of(data));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createCarRecord(carRecordCreateDTO)
        );

        String expectedMessage = "Car record with this register number already exists!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllCarRecords() {
        RegistrationCertificateData data = new RegistrationCertificateData(
                "C0030CB", LocalDate.now(), new CarModel( "Opel", "Opel", LocalDate.now(),
                new CarBrand("Opel", "Opel", "Opel",
                        List.of(new CarModel()), true), true), "frame", "engine",
                2000, 150, 90, 5, "red", "blue",
                1, VehicleTypeEnum.CAR);

        List<CarRecord> carRecords = List.of(
                new CarRecord(DrivingCategoryType.B, true, "description", data, FuelType.DIESEL, "555666",
                "User", LocalDate.now(), "", LocalDate.now(), 1000, 1,
                50, "Me", "My firm", "home", new CarPerson(), new CarPerson()
        ),
                new CarRecord(DrivingCategoryType.B, true, "description", data, FuelType.DIESEL, "555666",
                        "User", LocalDate.now(), "", LocalDate.now(), 1000, 1,
                        50, "Me", "My firm", "home", new CarPerson(), new CarPerson()
                ));
        //TODO why not found id
        carRecords.get(0).setId(1L);
        carRecords.get(1).setId(2L);
        List<CarRecordListDTO> carRecordListDTOS =
                List.of(
                        new CarRecordListDTO(1,"B","description","Me",
                                "C0030CB","Opel","Corsa","Ivan Ivanov",true ),
                        new CarRecordListDTO(2,"C","description","Petar",
                                "B1230CB","Honda","Jazz","Maria Ivanova",true ));


        when(mockCarRecordRepository.findAll()).thenReturn(carRecords);
        List<CarRecordListDTO> result = toTest.getAllCarRecord();
        Assertions.assertEquals(carRecordListDTOS.toArray().length, result.toArray().length);
        Assertions.assertEquals(carRecordListDTOS.get(0).getRegistrationNumber(), result.get(0).getRegistrationNumber());
        Assertions.assertEquals(carRecordListDTOS.get(0).getCarBrand(), result.get(0).getCarBrand());
    }

    @Test
    void testGetCarRecordById() {
        CarRecord carRecord = new CarRecord(DrivingCategoryType.B, true, "description",
                new RegistrationCertificateData(), FuelType.DIESEL, "555666", "User", LocalDate.now(),
                "", LocalDate.now(), 1000, 1, 50,
                "Me", "My firm", "home", new CarPerson(), new CarPerson()
        );

        CarRecordDTO carRecordDTO = new CarRecordDTO(1,"B", "description",  1000,
                1, 50,"555666", "DIESEL",
                new RegistrationCertificateDataDTO(),true , "User", "", "", "",
                "Me", "My firm", "home", new CarPersonDTO(), new CarPersonDTO()
        );

        Long id = 1L;

        when(mockCarRecordRepository.findById(id))
                .thenReturn(Optional.of(carRecord));

        CarRecordDTO result= toTest.getCarRecordById(id);

        assertEquals(carRecordDTO.getDrivingCategory(), result.getDrivingCategory());
        assertEquals(carRecordDTO.getDescription(), result.getDescription());
        assertEquals(carRecordDTO.getOwner(), result.getOwner());
    }

    @Test
    void testGetCarRecordByIdFailedCarRecordNotFound() {
        Long id = 1L;

        when(mockCarRecordRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.getCarRecordById(id)
        );

        String expectedMessage = "Car record is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteCarRecord() {
        CarRecord carRecord = new CarRecord(DrivingCategoryType.B, true, "description",
                new RegistrationCertificateData(), FuelType.DIESEL, "555666", "User", LocalDate.now(),
                "", LocalDate.now(), 1000, 1, 50,
                "Me", "My firm", "home", new CarPerson(), new CarPerson()
        );
        Long id = 1L;

        when(mockCarRecordRepository.findById(id))
                .thenReturn(Optional.of(carRecord));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteCarRecord(id)
        );

        String expectedMessage = "Car record successfully deleted!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteCarRecordFailedCarRecordNotFound() {
        Long id = 1L;
        when(mockCarRecordRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteCarRecord(id)
        );

        String expectedMessage = "Car record is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Disabled
    void testUpdateCarRecord() {
        CarRecord carRecord = new CarRecord(DrivingCategoryType.B, true, "description",
                new RegistrationCertificateData(), FuelType.DIESEL, "555666", "Admin", LocalDate.now(),
                "", LocalDate.now(), 1000, 1, 50,
                "Me", "My firm", "home", new CarPerson(), new CarPerson()
        );

        CarRecordEditDTO carRecordEditDTO = new CarRecordEditDTO(1,"B", "description",  1000,
                1, 50,"555666", "DIESEL",
                new RegistrationCertificateDataDTO(),true , "User", "", "", new CarPersonDTO(),
                new CarPersonDTO(), "Admin", "2024-01-01","" ,""
        );
        Long id = 1L;
        when(mockCarRecordRepository.findById(id))
                .thenReturn(Optional.of(carRecord));

        //TODO get currentUser
        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateCarRecord(id, carRecordEditDTO)
        );

        String expectedMessage = "Car record successfully updated!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockCarRecordRepository).save(carRecordArgumentCaptor.capture());
        CarRecord actualSavedEntity = carRecordArgumentCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(DrivingCategoryType.valueOf(carRecordEditDTO.getDrivingCategory()), actualSavedEntity.getDrivingCategory());
        assertEquals(FuelType.valueOf(carRecordEditDTO.getFuelType()), actualSavedEntity.getFuelType());
        assertEquals(carRecordEditDTO.getOwner(), actualSavedEntity.getOwner());
    }

    @Test
    void testUpdateCarRecordFailedCarRecordNotFound() {
        CarRecordEditDTO carRecordEditDTO = new CarRecordEditDTO(1,"B", "description",  1000,
                1, 50,"555666", "DIESEL",
                new RegistrationCertificateDataDTO(),true , "User", "", "", new CarPersonDTO(),
                new CarPersonDTO(), "Admin", "2024-01-01","" ,""
        );
        Long id = 1L;

        when(mockCarRecordRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateCarRecord(id, carRecordEditDTO)
        );

        String expectedMessage = "Car record is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateCarRecordFailedRegistrationNumberCannotBeChanged() {
        RegistrationCertificateData data =
                new RegistrationCertificateData("C0030CB", LocalDate.now(), new CarModel(), "frame",
                        "engine", 2000, 150, 90, 5, "red",
                        "blue", 1, VehicleTypeEnum.CAR);
        RegistrationCertificateDataDTO dataDTO =
                new RegistrationCertificateDataDTO("B1030CB", "2024-01-01",
                        new CarModelListDTO(), "frame", "engine", 2000, 150,
                        90, 5, "red", "blue", 1, "CAR");
        CarRecord carRecord = new CarRecord(
                DrivingCategoryType.B, true, "description",data, FuelType.DIESEL, "555666",
                "User", LocalDate.now(), "", LocalDate.now(), 1000, 1,
                50, "Me", "My firm", "home", new CarPerson(), new CarPerson()
        );

        CarRecordEditDTO carRecordEditDTO = new CarRecordEditDTO(1,"B", "description",  1000,
                1, 50,"555666", "DIESEL",
                dataDTO,true , "User", "", "", new CarPersonDTO(),
                new CarPersonDTO(), "Admin", "2024-01-01","" ,""
        );

        Long id = 1L;

        when(mockCarRecordRepository.findById(id))
                .thenReturn(Optional.of(carRecord));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateCarRecord(id, carRecordEditDTO)
        );

        String expectedMessage = "The registration number cannot be changed!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllRegisterNumbers() {
        List<RegistrationCertificateData> dataList =List.of(
                new RegistrationCertificateData("B1055TT", LocalDate.now(), new CarModel(), "frame",
                        "engine", 2000, 150, 90, 5, "red",
                        "blue", 1, VehicleTypeEnum.CAR),
                new RegistrationCertificateData("C0030CB", LocalDate.now(), new CarModel(), "frame",
                        "engine", 2000, 150, 90, 5, "red",
                        "blue", 1, VehicleTypeEnum.CAR)

        );
        dataList.get(0).setId(1L);
        dataList.get(1).setId(2L);

        List<RegistrationNumberDTO> registrationNumberDTOList = List.of(
              new RegistrationNumberDTO(1,"B1055TT"),
                new RegistrationNumberDTO(2,"C0030CB")
        );

        when(mockRegDataRepository.findAll())
                .thenReturn(dataList);

        List<RegistrationNumberDTO> result= toTest.getAllRegistrationNumber();

        Assertions.assertEquals(registrationNumberDTOList.toArray().length, result.toArray().length);
        Assertions.assertEquals(registrationNumberDTOList.get(0).getRegistrationNumber(), result.get(0).getRegistrationNumber());
        Assertions.assertEquals(registrationNumberDTOList.get(1).getRegistrationNumber(), result.get(1).getRegistrationNumber());
    }

    @Test
    void testGetCarRecordInfoByRegistrationCertificateDataId() {
        RegistrationCertificateData data =
                new RegistrationCertificateData("B1055TT", LocalDate.now(), new CarModel(), "frame",
                        "engine", 2000, 150, 90, 5, "red",
                        "blue", 1, VehicleTypeEnum.CAR);

        CarRecord carRecord = new CarRecord(DrivingCategoryType.B, true, "description",
                data, FuelType.DIESEL, "555666", "User", LocalDate.now(),
                "", LocalDate.now(), 1000, 1, 50,
                "Me", "My firm", "home",
                new CarPerson("Asen","Ivanov","0888995544",true,"Asen Ivanov"),
                new CarPerson()
        );

        List<Declaration> declarations =
                List.of(
                        new Declaration(carRecord, LocalDate.now(), "Aug.2024", 1000, 1500,
                                1, 1, 30, 3, "User", LocalDate.now(), "", LocalDate.now()),
                        new Declaration(carRecord, LocalDate.now().plusDays(1), "Jul.2024", 1500, 2000,
                                1, 1, 30, 3, "User", LocalDate.now(), "", LocalDate.now()));
        Long id = 1L;

        when(mockRegDataRepository.findById(id))
                .thenReturn(Optional.of(data));
        when(mockCarRecordRepository.findByRegistrationCertificateData(data))
                .thenReturn(carRecord);
        when(mockDeclarationRepository.findAll())
                .thenReturn(declarations);

        CarRecordInfoDTO result= toTest.getCarRecordInfoByRegistrationCertificateDataId(id);

        Assertions.assertEquals(data.getRegistrationNumber(), result.getRegistrationNumber());
        Assertions.assertEquals(carRecord.getResponsible().getFullName(), result.getResponsible());
        Assertions.assertEquals(declarations.get(1).getNewMileage(), result.getLastMileage());
    }
    @Test
    void testGetCarRecordInfoByRegistrationCertificateDataIdFailedDataNotFound() {
        Long id = 1L;
        when(mockRegDataRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.getCarRecordInfoByRegistrationCertificateDataId(id)
        );

        String expectedMessage = "Data is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void testGetCarRecordInfoByRegistrationCertificateDataIdNoDeclarations() {
        RegistrationCertificateData data =
                new RegistrationCertificateData("B1055TT", LocalDate.now(), new CarModel(), "frame",
                        "engine", 2000, 150, 90, 5, "red",
                        "blue", 1, VehicleTypeEnum.CAR);

        CarRecord carRecord = new CarRecord(DrivingCategoryType.B, true, "description",
                data, FuelType.DIESEL, "555666", "User", LocalDate.now(),
                "", LocalDate.now(), 1000, 1, 50,
                "Me", "My firm", "home",
                new CarPerson("Asen","Ivanov","0888995544",true,"Asen Ivanov"),
                new CarPerson()
        );

        List<Declaration> declarations = List.of();
        Long id = 1L;

        when(mockRegDataRepository.findById(id))
                .thenReturn(Optional.of(data));
        when(mockCarRecordRepository.findByRegistrationCertificateData(data))
                .thenReturn(carRecord);
        when(mockDeclarationRepository.findAll())
                .thenReturn(declarations);

        CarRecordInfoDTO result= toTest.getCarRecordInfoByRegistrationCertificateDataId(id);

        Assertions.assertEquals(data.getRegistrationNumber(), result.getRegistrationNumber());
        Assertions.assertEquals(carRecord.getResponsible().getFullName(), result.getResponsible());
        Assertions.assertEquals(carRecord.getTotalMileage(), result.getLastMileage());
    }
}