package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierListDTO;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelEntity;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelSupplier;
import my.project.vehiclefleetmanagement.repository.FuelRepository;
import my.project.vehiclefleetmanagement.repository.FuelSupplierRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FuelSupplierServiceImplTest {

    private FuelSupplierServiceImpl toTest;

    @Captor
    private ArgumentCaptor<FuelSupplier> fuelSupplierCaptor;

    @Mock
    private FuelRepository mockFuelRepository;
    @Mock
    private FuelSupplierRepository mockFuelSupplierRepository;

    @BeforeEach
    void setUp() {
        toTest = new FuelSupplierServiceImpl(
                mockFuelSupplierRepository,
                new ModelMapper(),
                mockFuelRepository);
    }

    @Test
    void testCreate() {
        FuelSupplierCreateDTO fuelSupplierCreateDTO = new FuelSupplierCreateDTO("OMV", "", true, List.of(1L));
        FuelEntity fuelEntity = new FuelEntity("LPG", "lpg", true);

        when(mockFuelSupplierRepository.findByName(fuelSupplierCreateDTO.getName()))
                .thenReturn(Optional.ofNullable(null));
        lenient().when(mockFuelRepository.findById(fuelSupplierCreateDTO.getFuelList().get(0)))
                .thenReturn(Optional.of(fuelEntity));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createFuelSupplier(fuelSupplierCreateDTO)
        );

        String expectedMessage = "Fuel supplier successfully created";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockFuelSupplierRepository).save(fuelSupplierCaptor.capture());
        FuelSupplier actualSavedEntity = fuelSupplierCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(fuelSupplierCreateDTO.getName(), actualSavedEntity.getName());
        assertEquals(fuelSupplierCreateDTO.getDescription(), actualSavedEntity.getDescription());
    }

    @Test
    void testCreateFailedSupplierExist() {
        FuelSupplier fuelSupplier = new FuelSupplier("OMV", "", List.of(new FuelEntity()), true);
        FuelSupplierCreateDTO fuelSupplierCreateDTO = new FuelSupplierCreateDTO("OMV", "", true, List.of(1L));

        when(mockFuelSupplierRepository.findByName(fuelSupplierCreateDTO.getName()))
                .thenReturn(Optional.of(fuelSupplier));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.createFuelSupplier(fuelSupplierCreateDTO)
        );

        String expectedMessage = "Fuel supplier already exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllSuppliers() {
        List<FuelSupplier> fuelSupplierList = List.of(
                new FuelSupplier("OMV", "", List.of(new FuelEntity()), true),
                new FuelSupplier("Shell", "", List.of(new FuelEntity()), true)
        );
        List<FuelSupplierListDTO> fuelSupplierListDTOS = List.of(
                new FuelSupplierListDTO(1, "OMV", "", List.of("Disel"), true),
                new FuelSupplierListDTO(2, "Shell", "", List.of("LPG"), true)
        );

        when(mockFuelSupplierRepository.findAll()).thenReturn(fuelSupplierList);

        toTest.getAllFuelSuppliers();

        Assertions.assertEquals(fuelSupplierListDTOS.toArray().length, fuelSupplierList.toArray().length);
    }

    @Test
    void testGetSupplierById() {
        FuelSupplier fuelSupplier = new FuelSupplier("OMV", "", List.of(new FuelEntity()), true);

        FuelSupplierDTO fuelSupplierDTO = new FuelSupplierDTO(1, "OMV", "", List.of(new FuelDTO()), true);

        Long id = 1L;

        when(mockFuelSupplierRepository.findById(id))
                .thenReturn(Optional.of(fuelSupplier));

        toTest.getFuelSupplierById(id);

        assertEquals(fuelSupplierDTO.getName(), fuelSupplier.getName());
        assertEquals(fuelSupplierDTO.getDescription(), fuelSupplier.getDescription());
        assertEquals(fuelSupplierDTO.getFuelList().size(), fuelSupplier.getFuelList().size());
    }

    @Test
    void testGetSupplierByIdFailedSupplierNotFound() {
        Long id = 1L;

        when(mockFuelSupplierRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.getFuelSupplierById(id)
        );

        String expectedMessage = "Fuel supplier is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteSupplier() {
        FuelSupplier fuelSupplier = new FuelSupplier("OMV", "", List.of(new FuelEntity()), true);
        Long id = 1L;

        when(mockFuelSupplierRepository.findById(id))
                .thenReturn(Optional.of(fuelSupplier));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteFuelSupplier(id)
        );

        String expectedMessage = "Fuel supplier successfully deleted!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteFailedSupplierNotFound() {
        Long id = 1L;
        when(mockFuelSupplierRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteFuelSupplier(id)
        );

        String expectedMessage = "Fuel supplier is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateSupplier() {
        FuelSupplier fuelSupplier = new FuelSupplier("OMV", "", List.of(new FuelEntity()), true);
        FuelSupplierEditDTO fuelSupplierEditDTO =
                new FuelSupplierEditDTO(1, "OMV", "desc", true, List.of(1L));

        FuelEntity fuelEntity = new FuelEntity("LPG", "lpg", true);
        Long id = 1L;

        when(mockFuelSupplierRepository.findById(id))
                .thenReturn(Optional.of(fuelSupplier));

       when(mockFuelRepository.findById(fuelSupplierEditDTO.getFuelList().get(0)))
                .thenReturn(Optional.of(fuelEntity));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateFuelSupplier(id, fuelSupplierEditDTO)
        );

        String expectedMessage = "Fuel supplier successfully updated!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockFuelSupplierRepository).save(fuelSupplierCaptor.capture());
        FuelSupplier actualSavedEntity = fuelSupplierCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(fuelSupplierEditDTO.getName(), actualSavedEntity.getName());
        assertEquals(fuelSupplierEditDTO.getFuelList().size(), actualSavedEntity.getFuelList().size());
        assertEquals(fuelSupplierEditDTO.getDescription(), actualSavedEntity.getDescription());
    }

    @Test
    void testUpdateSupplierFailedSupplierNotFound() {
        FuelSupplierEditDTO fuelSupplierEditDTO =
                new FuelSupplierEditDTO(1, "OMV", "desc", true, List.of(1L));
        Long id = 1L;

        when(mockFuelSupplierRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateFuelSupplier(id, fuelSupplierEditDTO)
        );

        String expectedMessage = "Fuel supplier is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}