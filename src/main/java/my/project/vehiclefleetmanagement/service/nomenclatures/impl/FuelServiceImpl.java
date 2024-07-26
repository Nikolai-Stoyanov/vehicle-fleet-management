package my.project.vehiclefleetmanagement.service.nomenclatures.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelListDTO;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelEntity;
import my.project.vehiclefleetmanagement.repository.nomenclatures.FuelRepository;
import my.project.vehiclefleetmanagement.service.nomenclatures.FuelService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FuelServiceImpl implements FuelService {
    private final FuelRepository fuelRepository;
    private final ModelMapper modelMapper;

    public FuelServiceImpl(FuelRepository fuelRepository, ModelMapper modelMapper) {
        this.fuelRepository = fuelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createFuel(FuelCreateDTO fuelCreateDTO) {
        Optional<FuelEntity> optionalFuel = this.fuelRepository.findByName(fuelCreateDTO.getName());

        if (optionalFuel.isPresent()) {
            throw new AppException("Fuel already exists", HttpStatus.BAD_REQUEST);
        }
        FuelEntity mappedEntity = modelMapper.map(fuelCreateDTO, FuelEntity.class);
        this.fuelRepository.save(mappedEntity);
        throw new AppException("Fuel successfully created", HttpStatus.OK);
    }

    @Override
    public List<FuelListDTO> getAllFuels() {
        List<FuelEntity> fuelEntityList = fuelRepository.findAll();

        List<FuelListDTO> fuelListDTOS = new ArrayList<>();
        for (FuelEntity fuel : fuelEntityList) {
            FuelListDTO model = modelMapper.map(fuel, FuelListDTO.class);
            fuelListDTOS.add(model);
        }
        return fuelListDTOS;
    }

    @Override
    public FuelDTO getFuelById(Long id) {
        Optional<FuelEntity> optionalFuel = this.fuelRepository.findById(id);
        if (optionalFuel.isEmpty()) {
            throw new AppException("Fuel is not found!", HttpStatus.NOT_FOUND);
        }
        return optionalFuel.map(fuel -> modelMapper.map(fuel, FuelDTO.class))
                .orElseThrow(() -> new AppException("Unknown error", HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Override
    public void deleteFuel(Long id) {
        Optional<FuelEntity> optionalFuel = this.fuelRepository.findById(id);
        if (optionalFuel.isEmpty()) {
            throw new AppException("Fuel is not found!", HttpStatus.NOT_FOUND);
        }
        this.fuelRepository.deleteById(id);
        throw new AppException("Fuel successfully deleted!", HttpStatus.OK);
    }

    @Override
    public void updateFuel(Long id, FuelEditDTO fuelEditDTO) {
        Optional<FuelEntity> optionalFuel = this.fuelRepository.findById(id);
        if (optionalFuel.isEmpty()) {
            throw new AppException("Fuel is not found!", HttpStatus.NOT_FOUND);
        }
        FuelEntity mappedEntity = modelMapper.map(fuelEditDTO, FuelEntity.class);
        this.fuelRepository.save(mappedEntity);
        throw new AppException("Fuel successfully updated!", HttpStatus.OK);
    }
}
