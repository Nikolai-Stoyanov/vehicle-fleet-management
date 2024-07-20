package my.project.vehiclefleetmanagement.service.nomenclatures.impl;

import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierListDTO;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelEntity;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelSupplier;
import my.project.vehiclefleetmanagement.repository.nomenclatures.FuelRepository;
import my.project.vehiclefleetmanagement.repository.nomenclatures.FuelSupplierRepository;
import my.project.vehiclefleetmanagement.service.nomenclatures.FuelSupplierService;
import org.modelmapper.ModelMapper;
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
    public boolean createFuelSupplier(FuelSupplierCreateDTO fuelSupplierCreateDTO) {
        Optional<FuelSupplier> optionalFuelSupplier = this.fuelSupplierRepository.findByName( fuelSupplierCreateDTO.getName());

        if (optionalFuelSupplier.isPresent()) {
            return false;
        }
        FuelSupplier mappedEntity = modelMapper.map(fuelSupplierCreateDTO, FuelSupplier.class);
        mappedEntity.setFuelList(this.getFuelsList(fuelSupplierCreateDTO.getFuelList()));
        this.fuelSupplierRepository.save(mappedEntity);
        return true;
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
        return fuelSupplierOptional.map(fuelSupplier -> modelMapper.map(fuelSupplier, FuelSupplierDTO.class)).orElse(null);
    }

    @Override
    public void deleteFuelSupplier(Long id) {
        this.fuelSupplierRepository.deleteById(id);
    }

    @Override
    public boolean updateFuelSupplier(Long id, FuelSupplierEditDTO fuelSupplierEditDTO) {
        Optional<FuelSupplier> fuelSupplierOptional = this.fuelSupplierRepository.findById(id);
        if (fuelSupplierOptional.isEmpty()) {
            return false;
        }
        FuelSupplier mappedEntity = modelMapper.map(fuelSupplierEditDTO, FuelSupplier.class);


        mappedEntity.setFuelList(this.getFuelsList(fuelSupplierEditDTO.getFuelList()));
        this.fuelSupplierRepository.save(mappedEntity);
        return true;
    }

   private List<FuelEntity> getFuelsList(List<Long> list){
       List<FuelEntity> fuelEntities = new ArrayList<>();
       for (long fuelId : list) {
           Optional<FuelEntity> optionalFuel = this.fuelRepository.findById( fuelId);
           optionalFuel.ifPresent(fuelEntities::add);
       }
        return fuelEntities;
    }
}
