package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandListDTO;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarBrand;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarModel;
import my.project.vehiclefleetmanagement.repository.CarBrandRepository;
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
public class CarBrandServiceImplTest {

    private CarBrandServiceImpl toTest;

    @Captor
    private ArgumentCaptor<CarBrand> carBrandCaptor;

    @Mock
    private CarBrandRepository mockCarBrandRepository;


    @BeforeEach
    void setUp() {
        toTest = new CarBrandServiceImpl(
                mockCarBrandRepository,
                new ModelMapper());
    }

    @Test
    void testCreateBrand() {
        CarBrandCreateDTO carBrandCreateDTO = new CarBrandCreateDTO("Opel", "", "Opel", true);

        when(mockCarBrandRepository.findByName(carBrandCreateDTO.getName()))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createBrand(carBrandCreateDTO)
        );

        String expectedMessage = "Car brand successfully created";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockCarBrandRepository).save(carBrandCaptor.capture());
        CarBrand actualSavedEntity = carBrandCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(carBrandCreateDTO.getName(), actualSavedEntity.getName());
        assertEquals(carBrandCreateDTO.getDescription(), actualSavedEntity.getDescription());
        assertEquals(carBrandCreateDTO.getCompany(), actualSavedEntity.getCompany());
    }

    @Test
    void testCreateBrandFailedBrandExist() {
        CarBrand carBrand = new CarBrand("Opel", "Opel", "Opel",
                List.of(new CarModel("Corsa", "", LocalDate.now(), new CarBrand(), true)), true);
        CarBrandCreateDTO carBrandCreateDTO = new CarBrandCreateDTO("Opel", "", "Opel", true);

        when(mockCarBrandRepository.findByName(carBrandCreateDTO.getName()))
                .thenReturn(Optional.of(carBrand));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createBrand(carBrandCreateDTO)
        );

        String expectedMessage = "Car brand already exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllBrands() {
        List<CarBrand> carBrandList = List.of(
                new CarBrand("Opel", "Opel", "Opel",
                        List.of(new CarModel("Corsa", "", LocalDate.now(), new CarBrand(), true)), true),
                new CarBrand("Ford", "", "Ford",
                        List.of(new CarModel("Ka", "", LocalDate.now(), new CarBrand(), true)), true)
        );
        List<CarBrandListDTO> carBrandListDTOS = List.of(
                new CarBrandListDTO(1, "Opel", "", "Opel", List.of("Corsa"), true),
                new CarBrandListDTO(2, "Ford", "", "Ford", List.of("Ka"), true)
        );

        when(mockCarBrandRepository.findAll()).thenReturn(carBrandList);

        List<CarBrandListDTO> result=  toTest.getAllBrands();

        Assertions.assertEquals(carBrandListDTOS.toArray().length, result.toArray().length);
        Assertions.assertEquals(carBrandListDTOS.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(carBrandListDTOS.get(1).getCompany(), result.get(1).getCompany());
    }

    @Test
    void testGetBrandById() {
        CarBrand carBrand = new CarBrand("Opel", "Opel", "Opel",
                List.of(new CarModel("Corsa", "", LocalDate.now(), new CarBrand(), true)), true);

        CarBrandDTO carBrandListDTO = new CarBrandDTO(1, "Opel", "", "Opel",
                List.of("Corsa"), true);

        Long id = 1L;

        when(mockCarBrandRepository.findById(id))
                .thenReturn(Optional.of(carBrand));


        CarBrandDTO result=  toTest.getBrandById(id);

        assertEquals(carBrandListDTO.getName(), result.getName());
        assertEquals(carBrandListDTO.getCompany(), result.getCompany());
        assertEquals(carBrandListDTO.getModels().toArray().length, result.getModels().toArray().length);
    }

    @Test
    void testDeleteCarBrand() {
        CarBrand carBrand = new CarBrand("Opel", "Opel", "Opel",
                List.of(new CarModel("Corsa", "", LocalDate.now(), new CarBrand(), true)), true);

        Long id = 1L;

        when(mockCarBrandRepository.findById(id))
                .thenReturn(Optional.of(carBrand));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteBrand(id)
        );

        String expectedMessage = "Car brand successfully deleted!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteFailedCarBrandNotFound() {
        Long id = 1L;
        when(mockCarBrandRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteBrand(id)
        );

        String expectedMessage = "Car brand is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateBrand() {
        CarBrand carBrand = new CarBrand("Opel", "Opel", "Opel",
                List.of(new CarModel("Corsa", "", LocalDate.now(), new CarBrand(), true)), true);
        CarBrandEditDTO carBrandEditDTO =
                new CarBrandEditDTO(1,"Opel", "op", "Opel AD", List.of("Corsa"), true);
        Long id = 1L;

        when(mockCarBrandRepository.findById(id))
                .thenReturn(Optional.of(carBrand));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateBrand(id, carBrandEditDTO)
        );

        String expectedMessage = "Car brand successfully updated!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockCarBrandRepository).save(carBrandCaptor.capture());
        CarBrand actualSavedEntity = carBrandCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(carBrandEditDTO.getName(), actualSavedEntity.getName());
        assertEquals(carBrandEditDTO.getCompany(), actualSavedEntity.getCompany());
        assertEquals(carBrandEditDTO.getDescription(), actualSavedEntity.getDescription());
    }

    @Test
    void testUpdateBrandFailedBrandNotFound() {
        CarBrandEditDTO carBrandEditDTO =
                new CarBrandEditDTO(1,"Opel", "op", "Opel AD", List.of("Corsa"), true);
        Long id = 1L;

        when(mockCarBrandRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateBrand(id, carBrandEditDTO)
        );

        String expectedMessage = "Car brand is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void testUpdateBrandNameFailedBrandNameExist() {
        CarBrand carBrand1 = new CarBrand("Opel", "Opel", "Opel",
                List.of(new CarModel()), true);
        CarBrand carBrand2= new CarBrand("Honda", "Honda", "Honda", List.of(new CarModel()), true);
        CarBrandEditDTO carBrandEditDTO =
                new CarBrandEditDTO(1,"Honda", "op", "Opel AD", List.of("Corsa"), true);
        Long id = 1L;

        when(mockCarBrandRepository.findById(id))
                .thenReturn(Optional.of(carBrand1));
        when(mockCarBrandRepository.findByName(carBrandEditDTO.getName()))
                .thenReturn(Optional.of(carBrand2));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateBrand(id, carBrandEditDTO)
        );

        String expectedMessage = "Car brand with this name already exists!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}