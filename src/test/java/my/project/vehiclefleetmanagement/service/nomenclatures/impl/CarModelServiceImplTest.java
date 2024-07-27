package my.project.vehiclefleetmanagement.service.nomenclatures.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.BrandDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelListDTO;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarBrand;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarModel;
import my.project.vehiclefleetmanagement.repository.nomenclatures.CarBrandRepository;
import my.project.vehiclefleetmanagement.repository.nomenclatures.CarModelRepository;
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
public class CarModelServiceImplTest {

    private CarModelServiceImpl toTest;

    @Captor
    private ArgumentCaptor<CarModel> carModelCaptor;

    @Mock
    private CarModelRepository mockCarModelRepository;

    @Mock
    private CarBrandRepository mockCarBrandRepository;

    @BeforeEach
    void setUp() {
        toTest = new CarModelServiceImpl(
                mockCarModelRepository,
                new ModelMapper(),
                mockCarBrandRepository);
    }

    @Test
    void testCreate() {
        CarModelCreateDTO carModelCreateDTO = new CarModelCreateDTO("Corsa", "", LocalDate.now(), 1, true);
        CarBrand carBrand = new CarBrand("Opel", "Opel", "Opel",
                List.of(new CarModel("Corsa", "", LocalDate.now(), new CarBrand(), true)), true);

        when(mockCarModelRepository.findByName(carModelCreateDTO.getName()))
                .thenReturn(Optional.ofNullable(null));
        when(mockCarBrandRepository.findById(carModelCreateDTO.getBrand()))
                .thenReturn(Optional.of(carBrand));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createModel(carModelCreateDTO)
        );

        String expectedMessage = "Car model successfully created";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockCarModelRepository).save(carModelCaptor.capture());
        CarModel actualSavedEntity = carModelCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(carModelCreateDTO.getName(), actualSavedEntity.getName());
        assertEquals(carModelCreateDTO.getDescription(), actualSavedEntity.getDescription());
    }

    @Test
    void testCreateFailedModelExist() {
        CarModel carModel = new CarModel("Opel", "Opel", LocalDate.now(),
                new CarBrand(), true);
        CarModelCreateDTO carModelCreateDTO = new CarModelCreateDTO("Corsa", "", LocalDate.now(), 1, true);

        when(mockCarModelRepository.findByName(carModelCreateDTO.getName()))
                .thenReturn(Optional.of(carModel));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createModel(carModelCreateDTO)
        );

        String expectedMessage = "Car model already exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreateFailedBrandExist() {
        CarModelCreateDTO carModelCreateDTO = new CarModelCreateDTO("Corsa", "", LocalDate.now(), 1, true);

        when(mockCarModelRepository.findByName(carModelCreateDTO.getName()))
                .thenReturn(Optional.ofNullable(null));
        when(mockCarBrandRepository.findById(carModelCreateDTO.getBrand()))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createModel(carModelCreateDTO)
        );

        String expectedMessage = "Car brand not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllModels() {
        List<CarModel> carModelList = List.of(
                new CarModel("Opel", "Opel", LocalDate.now(),
                        new CarBrand("Opel", "Opel", "Opel",
                List.of(new CarModel()), true), true),
                 new CarModel("Opel", "Opel", LocalDate.now(),
                         new CarBrand("Ford", "Ford", "Ford",
                                 List.of(new CarModel()), true), true)
        );
        List<CarModelListDTO> carModelListDTOS = List.of(
                new CarModelListDTO(1, "Corsa", "", LocalDate.now(), "Opel", true),
                new CarModelListDTO(2, "Ka", "",  LocalDate.now(), "Ford", true)
        );
        CarBrand carBrand = new CarBrand("Opel", "Opel", "Opel",
                List.of(new CarModel()), true);

        when(mockCarModelRepository.findAll()).thenReturn(carModelList);
        when(mockCarBrandRepository.findById(carModelList.get(1).getBrand().getId())).thenReturn(Optional.of(carBrand));

        toTest.getAllModels();

        Assertions.assertEquals(carModelListDTOS.toArray().length, carModelList.toArray().length);
    }

    @Test
    void testGetModelById() {
        CarModel carModel = new CarModel("Opel", "Opel", LocalDate.now(),
                new CarBrand(), true);

        CarModelDTO carModelDTO = new CarModelDTO(1, "Opel", "", LocalDate.now(),
              new BrandDTO(), true);

        Long id = 1L;

        when(mockCarModelRepository.findById(id))
                .thenReturn(Optional.of(carModel));


        toTest.getModelById(id);

        assertEquals(carModelDTO.getName(), carModel.getName());
        assertEquals(carModelDTO.getYear(), carModel.getYear());
        assertEquals(carModelDTO.getBrand().getName(), carModel.getBrand().getName());
    }

    @Test
    void testGetModelByIdFailedModelNotFound() {
        Long id = 1L;

        when(mockCarModelRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));


                Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.getModelById(id)
        );

        String expectedMessage = "Car model is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteCarModel() {
        CarModel carModel = new CarModel("Opel", "Opel", LocalDate.now(),
                new CarBrand(), true);
        Long id = 1L;

        when(mockCarModelRepository.findById(id))
                .thenReturn(Optional.of(carModel));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteModel(id)
        );

        String expectedMessage = "Car model successfully deleted!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteFailedCarModelNotFound() {
        Long id = 1L;
        when(mockCarModelRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteModel(id)
        );

        String expectedMessage = "Car model is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateModel() {
        CarModel carModel = new CarModel("Opel", "Opel", LocalDate.now(),
                new CarBrand(), true);
        CarModelEditDTO carModelEditDTO =
                new CarModelEditDTO(1,"Opel", "op", LocalDate.now(),1, true);
        CarBrand carBrand = new CarBrand("Opel", "Opel", "Opel", List.of(new CarModel()), true);
        Long id = 1L;

        when(mockCarModelRepository.findById(id))
                .thenReturn(Optional.of(carModel));
        when(mockCarBrandRepository.findById(carModelEditDTO.getBrand()))
                .thenReturn(Optional.of(carBrand));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateModel(id, carModelEditDTO)
        );

        String expectedMessage = "Car model successfully updated!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockCarModelRepository).save(carModelCaptor.capture());
        CarModel actualSavedEntity = carModelCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(carModelEditDTO.getName(), actualSavedEntity.getName());
        assertEquals(carModelEditDTO.getYear(), actualSavedEntity.getYear());
        assertEquals(carModelEditDTO.getDescription(), actualSavedEntity.getDescription());
    }

    @Test
    void testUpdateModelFailedModelNotFound() {
        CarModelEditDTO carModelEditDTO =
                new CarModelEditDTO(1,"Opel", "op", LocalDate.now(),1, true);
        Long id = 1L;

        when(mockCarModelRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateModel(id, carModelEditDTO)
        );

        String expectedMessage = "Car model is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateUserFailedUserNotFound() {
        CarModel carModel = new CarModel("Opel", "Opel", LocalDate.now(),
                new CarBrand(), true);
        CarModelEditDTO carModelEditDTO =
                new CarModelEditDTO(1,"Opel", "op", LocalDate.now(),1, true);
        Long id = 1L;

        when(mockCarModelRepository.findById(id))
                .thenReturn(Optional.of(carModel));
        when(mockCarBrandRepository.findById(carModelEditDTO.getBrand()))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateModel(id, carModelEditDTO)
        );

        String expectedMessage = "Car brand not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}