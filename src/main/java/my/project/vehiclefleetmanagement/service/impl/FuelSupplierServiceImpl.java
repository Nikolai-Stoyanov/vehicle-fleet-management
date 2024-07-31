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
import my.project.vehiclefleetmanagement.service.FuelSupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FuelSupplierServiceImpl implements FuelSupplierService {

    private final FuelSupplierRepository fuelSupplierRepository;
    private final ModelMapper modelMapper;
    private final FuelRepository fuelRepository;

    public FuelSupplierServiceImpl(FuelSupplierRepository fuelSupplierRepository, ModelMapper modelMapper, FuelRepository fuelRepository) {
        this.fuelSupplierRepository = fuelSupplierRepository;
        this.modelMapper = modelMapper;
        this.fuelRepository = fuelRepository;
    }

    @Override
    public void createFuelSupplier(FuelSupplierCreateDTO fuelSupplierCreateDTO) {
        Optional<FuelSupplier> optionalFuelSupplier = this.fuelSupplierRepository.findByName( fuelSupplierCreateDTO.getName());

        if (optionalFuelSupplier.isPresent()) {
            throw new AppException("Fuel supplier already exists", HttpStatus.BAD_REQUEST);
        }
        FuelSupplier mappedEntity = modelMapper.map(fuelSupplierCreateDTO, FuelSupplier.class);

       List<FuelEntity> fuelEntities = new ArrayList<>();
       for (Long id : fuelSupplierCreateDTO.getFuelList()) {
           Optional<FuelEntity> optionalFuelEntity = this.fuelRepository.findById(id);
           optionalFuelEntity.ifPresent(fuelEntities::add);
       }
        mappedEntity.setFuelList(fuelEntities);
        this.fuelSupplierRepository.save(mappedEntity);
        throw new AppException("Fuel supplier successfully created", HttpStatus.OK);
    }

    @Override
    public List<FuelSupplierListDTO> getAllFuelSuppliers() {
        List<FuelSupplier> fuelSupplierList=  this.fuelSupplierRepository.findAll();
        List<FuelSupplierListDTO> fuelSupplierListDTOS = new ArrayList<>();
        for (FuelSupplier fuelSupplier : fuelSupplierList) {
            FuelSupplierListDTO mappedEntity=modelMapper.map(fuelSupplier, FuelSupplierListDTO.class);

            List<String> fuelNames = new ArrayList<>();
            for (FuelEntity fuel : fuelSupplier.getFuelList()) {
                fuelNames.add(fuel.getName());
            }
            mappedEntity.setFuelList(fuelNames);

            fuelSupplierListDTOS.add(mappedEntity);
        }
        return fuelSupplierListDTOS;
    }

    @Override
    public FuelSupplierDTO getFuelSupplierById(Long id) {
        Optional<FuelSupplier> fuelSupplierOptional = this.fuelSupplierRepository.findById(id);
        if (fuelSupplierOptional.isEmpty()) {
            throw new AppException("Fuel supplier is not found!", HttpStatus.NOT_FOUND);
        }
        FuelSupplierDTO fuelSupplierDTO= modelMapper.map(fuelSupplierOptional, FuelSupplierDTO.class);

        List<FuelDTO> fuelDTOS = new ArrayList<>();
        for (FuelEntity fuel : fuelSupplierOptional.get().getFuelList()) {
            fuelDTOS.add(modelMapper.map(fuel, FuelDTO.class));
        }
        fuelSupplierDTO.setFuelList(fuelDTOS);
        return fuelSupplierDTO;
    }

    @Override
    public void deleteFuelSupplier(Long id) {
        Optional<FuelSupplier> fuelSupplierOptional = this.fuelSupplierRepository.findById(id);
        if (fuelSupplierOptional.isEmpty()) {
            throw new AppException("Fuel supplier is not found!", HttpStatus.NOT_FOUND);
        }
        this.fuelSupplierRepository.deleteById(id);
        throw new AppException("Fuel supplier successfully deleted!", HttpStatus.OK);
    }

    @Override
    public void updateFuelSupplier(Long id, FuelSupplierEditDTO fuelSupplierEditDTO) {
        Optional<FuelSupplier> fuelSupplierOptional = this.fuelSupplierRepository.findById(id);
        if (fuelSupplierOptional.isEmpty()) {
            throw new AppException("Fuel supplier is not found!", HttpStatus.NOT_FOUND);
        }
        FuelSupplier mappedEntity = modelMapper.map(fuelSupplierEditDTO, FuelSupplier.class);
        List<FuelEntity> fuelEntities = new ArrayList<>();
        for (long fuelId : fuelSupplierEditDTO.getFuelList()) {
            Optional<FuelEntity> optionalFuel = this.fuelRepository.findById( fuelId);
            optionalFuel.ifPresent(fuelEntities::add);
        }

        mappedEntity.setFuelList(fuelEntities);
        this.fuelSupplierRepository.save(mappedEntity);
        throw new AppException("Fuel supplier successfully updated!", HttpStatus.OK);
    }

}
