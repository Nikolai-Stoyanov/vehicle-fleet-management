package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandListDTO;
import my.project.vehiclefleetmanagement.model.entity.car.CarPerson;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarBrand;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarModel;
import my.project.vehiclefleetmanagement.repository.CarBrandRepository;
import my.project.vehiclefleetmanagement.repository.CarPersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
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
public class CarPersonServiceImplTest {

    private CarPersonServiceImpl toTest;

    @Captor
    private ArgumentCaptor<CarPerson> carPersonArgumentCaptor;

    @Mock
    private CarPersonRepository mockCarPersonRepository;


    @BeforeEach
    void setUp() {
        toTest = new CarPersonServiceImpl(
                mockCarPersonRepository,
                new ModelMapper());
    }

    @Test
    void testCreatePerson() {
        CarPersonCreateDTO carPersonCreateDTO = new CarPersonCreateDTO("Ivan", "Petrov", "0899112233", true);

        when(mockCarPersonRepository.findByPhoneNumber(carPersonCreateDTO.getPhoneNumber()))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createCarPerson(carPersonCreateDTO)
        );

        String expectedMessage = "Person successfully created";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockCarPersonRepository).save(carPersonArgumentCaptor.capture());
        CarPerson actualSavedEntity = carPersonArgumentCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(carPersonCreateDTO.getFirstName(), actualSavedEntity.getFirstName());
        assertEquals(carPersonCreateDTO.getLastName(), actualSavedEntity.getLastName());
        assertEquals(carPersonCreateDTO.getPhoneNumber(), actualSavedEntity.getPhoneNumber());
    }

    @Test
    void testCreatePersonFailedPersonExist() {
        CarPerson carPerson = new CarPerson("Ivan", "Petrov", "0899112233", true, "Ivan Petrov");
        CarPersonCreateDTO carPersonCreateDTO = new CarPersonCreateDTO("Ivan", "Petrov", "0899112233", true);

        when(mockCarPersonRepository.findByPhoneNumber(carPersonCreateDTO.getPhoneNumber()))
                .thenReturn(Optional.of(carPerson));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createCarPerson(carPersonCreateDTO)
        );

        String expectedMessage = "Person with phone number 0899112233 is already exists!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllPersons() {
        List<CarPerson> carPeople = List.of(
                new CarPerson("Ivan", "Petrov", "0899112233", true, "Ivan Petrov"),
        new CarPerson("Petar", "Ivanov", "0899115544", true, "Petar Ivanov")
        );

        List<CarPersonDTO> carPersonDTOS = List.of(
                new CarPersonDTO(1, "Ivan", "Petrov", "0899112233","Ivan Petrov", true),
                new CarPersonDTO(2, "Petar", "Ivanov", "0899115544", "Petar Ivanov", true)
        );

        when(mockCarPersonRepository.findAll()).thenReturn(carPeople);
        List<CarPersonDTO> result= toTest.getAllCarPerson();
        Assertions.assertEquals(carPersonDTOS.toArray().length, result.toArray().length);
        Assertions.assertEquals(carPersonDTOS.get(0).getPhoneNumber(), result.get(0).getPhoneNumber());
        Assertions.assertEquals(carPersonDTOS.get(1).getFirstName(), result.get(1).getFirstName());
    }

    @Test
    void testGetPersonById() {
        CarPerson carPerson = new CarPerson("Ivan", "Petrov", "0899112233",
                true, "Ivan Petrov");

        CarPersonDTO personDTO = new CarPersonDTO(1, "Ivan", "Petrov", "0899112233",
                "Ivan Petrov", true);

        Long id = 1L;

        when(mockCarPersonRepository.findById(id))
                .thenReturn(Optional.of(carPerson));

        CarPersonDTO result= toTest.getCarPersonById(id);

        assertEquals(personDTO.getFirstName(), result.getFirstName());
        assertEquals(personDTO.getLastName(), result.getLastName());
        assertEquals(personDTO.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    void testGetPersonByIdFailedPersonNotFound() {
        Long id = 1L;

        when(mockCarPersonRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.getCarPersonById(id)
        );

        String expectedMessage = "Person is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeletePerson() {
        CarPerson carPerson = new CarPerson("Ivan", "Petrov", "0899112233",
                true, "Ivan Petrov");
        Long id = 1L;

        when(mockCarPersonRepository.findById(id))
                .thenReturn(Optional.of(carPerson));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteCarPerson(id)
        );

        String expectedMessage = "Person successfully deleted!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeletePersonFailedPersonNotFound() {
        Long id = 1L;
        when(mockCarPersonRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteCarPerson(id)
        );

        String expectedMessage = "Person is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdatePerson() {
        CarPerson carPerson = new CarPerson("Ivan", "Petrov", "0899112233", true, "Ivan Petrov");
        CarPersonEditDTO carPersonEditDTO = new CarPersonEditDTO(1,"Ivan", "Petrov", "0899112233", true);
        Long id = 1L;

        when(mockCarPersonRepository.findById(id))
                .thenReturn(Optional.of(carPerson));
        when(mockCarPersonRepository.findByPhoneNumber(carPersonEditDTO.getPhoneNumber()))
                .thenReturn(Optional.ofNullable(null));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateCarPerson(id, carPersonEditDTO)
        );

        String expectedMessage = "Person successfully updated!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockCarPersonRepository).save(carPersonArgumentCaptor.capture());
        CarPerson actualSavedEntity = carPersonArgumentCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(carPersonEditDTO.getFirstName(), actualSavedEntity.getFirstName());
        assertEquals(carPersonEditDTO.getLastName(), actualSavedEntity.getLastName());
        assertEquals(carPersonEditDTO.getPhoneNumber(), actualSavedEntity.getPhoneNumber());
    }

    @Test
    void testUpdatePersonFailedPersonNotFound() {
        CarPersonEditDTO carPersonEditDTO = new CarPersonEditDTO(1,"Ivan", "Petrov", "0899112233", true);
        Long id = 1L;

        when(mockCarPersonRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateCarPerson(id, carPersonEditDTO)
        );

        String expectedMessage = "Person is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void testUpdatePersonPhoneNumberFailedPhoneNumberExist() {
        CarPerson carPerson1 =new CarPerson("Ivan", "Petrov", "0899112233", true, "Ivan Petrov");
        CarPerson carPerson2 =  new CarPerson("Petar", "Ivanov", "0899115544", true, "Petar Ivanov");

        CarPersonEditDTO carPersonEditDTO = new CarPersonEditDTO(1,"Ivan", "Petrov", "0899115544", true);
        Long id = 1L;

        when(mockCarPersonRepository.findById(id))
                .thenReturn(Optional.of(carPerson1));
        when(mockCarPersonRepository.findByPhoneNumber(carPersonEditDTO.getPhoneNumber()))
                .thenReturn(Optional.of(carPerson2));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateCarPerson(id, carPersonEditDTO)
        );

        String expectedMessage = "Person with phone number 0899115544 is already exists!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}