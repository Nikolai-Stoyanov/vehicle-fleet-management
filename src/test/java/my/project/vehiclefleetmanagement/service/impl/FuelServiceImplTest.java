package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelListDTO;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelEntity;
import my.project.vehiclefleetmanagement.repository.FuelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FuelServiceImplTest {

    private FuelServiceImpl toTest;

    @Captor
    private ArgumentCaptor<FuelEntity> fuelEntityArgumentCaptor;

    @Mock
    private FuelRepository mockFuelRepository;


    @BeforeEach
    void setUp() {
        toTest = new FuelServiceImpl(
                mockFuelRepository,
                new ModelMapper());
    }

    @Test
    void testCreate() {
        FuelCreateDTO fuelCreateDTO = new FuelCreateDTO("LPG", "", true);

        when(mockFuelRepository.findByName(fuelCreateDTO.getName()))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createFuel(fuelCreateDTO)
        );

        String expectedMessage = "Fuel successfully created";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockFuelRepository).save(fuelEntityArgumentCaptor.capture());
        FuelEntity actualSavedEntity = fuelEntityArgumentCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(fuelCreateDTO.getName(), actualSavedEntity.getName());
        assertEquals(fuelCreateDTO.getDescription(), actualSavedEntity.getDescription());
    }

    @Test
    void testCreateFailedFuelExist() {
        FuelEntity fuelEntity = new FuelEntity("LPG", "lpg", true);
        FuelCreateDTO fuelCreateDTO = new FuelCreateDTO("LPG", "", true);

        when(mockFuelRepository.findByName(fuelCreateDTO.getName()))
                .thenReturn(Optional.of(fuelEntity));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createFuel(fuelCreateDTO)
        );

        String expectedMessage = "Fuel already exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllFuels() {
        List<FuelEntity> fuelEntityList = List.of(
                new FuelEntity("LPG", "lpg", true),
                new FuelEntity("Diesel", "", true)
        );
        List<FuelListDTO> fuelListDTOS = List.of(
                new FuelListDTO(1, "LPG", "lpg", true),
                new FuelListDTO(2, "Diesel", "", true)
        );

        when(mockFuelRepository.findAll()).thenReturn(fuelEntityList);

        List<FuelListDTO> result= toTest.getAllFuels();

        Assertions.assertEquals(fuelListDTOS.toArray().length, result.toArray().length);
        Assertions.assertEquals(fuelListDTOS.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(fuelListDTOS.get(1).getDescription(), result.get(1).getDescription());
    }

    @Test
    void testGetFuelById() {
        FuelEntity fuelEntity = new FuelEntity("LPG", "lpg", true);
        FuelDTO fuelDTO = new FuelDTO(1, "LPG", "lpg", true);

        Long id = 1L;

        when(mockFuelRepository.findById(id)).thenReturn(Optional.of(fuelEntity));

        FuelDTO result= toTest.getFuelById(id);

        assertEquals(fuelDTO.getName(), result.getName());
        assertEquals(fuelDTO.getDescription(), result.getDescription());
    }

    @Test
    void testGetFuelByIdFailedFuelNotFound() {
        Long id = 1L;

        when(mockFuelRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.getFuelById(id)
        );

        String expectedMessage = "Fuel is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteFuel() {
        FuelEntity fuelEntity = new FuelEntity("LPG", "lpg", true);
        Long id = 1L;

        when(mockFuelRepository.findById(id))
                .thenReturn(Optional.of(fuelEntity));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteFuel(id)
        );

        String expectedMessage = "Fuel successfully deleted!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteFailedFuelNotFound() {
        Long id = 1L;
        when(mockFuelRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteFuel(id)
        );

        String expectedMessage = "Fuel is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateFuel() {
        FuelEntity fuelEntity = new FuelEntity("LPG", "lpg", true);
        FuelEditDTO fuelEditDTO =
                new FuelEditDTO(1,"LPG", "op op",  true);
        Long id = 1L;

        when(mockFuelRepository.findById(id))
                .thenReturn(Optional.of(fuelEntity));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateFuel(id, fuelEditDTO)
        );

        String expectedMessage = "Fuel successfully updated!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockFuelRepository).save(fuelEntityArgumentCaptor.capture());
        FuelEntity actualSavedEntity = fuelEntityArgumentCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(fuelEditDTO.getName(), actualSavedEntity.getName());
        assertEquals(fuelEditDTO.getDescription(), actualSavedEntity.getDescription());
    }

    @Test
    void testUpdateFuelFailedFuelNotFound() {
        FuelEditDTO fuelEditDTO =
                new FuelEditDTO(1,"LPG", "lpg",  true);
        Long id = 1L;

        when(mockFuelRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateFuel(id, fuelEditDTO)
        );

        String expectedMessage = "Fuel is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateFuelNameFailedNameExist() {
        FuelEntity fuelEntity1 = new FuelEntity("LPG", "lpg", true);
        FuelEntity fuelEntity2 = new FuelEntity("Mega Diesel", "lpg", true);
        FuelEditDTO fuelEditDTO =
                new FuelEditDTO(1,"Mega Diesel", "diesel",  true);
        Long id = 1L;

        when(mockFuelRepository.findById(id))
                .thenReturn(Optional.of(fuelEntity1));
        when(mockFuelRepository.findByName(fuelEditDTO.getName()))
                .thenReturn(Optional.of(fuelEntity2));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateFuel(id, fuelEditDTO)
        );

        String expectedMessage = "Fuel with name Mega Diesel is already exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}