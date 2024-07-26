package my.project.vehiclefleetmanagement.service.car.impl;

import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonEditDTO;
import my.project.vehiclefleetmanagement.model.entity.car.CarPerson;
import my.project.vehiclefleetmanagement.model.entity.car.Owner;
import my.project.vehiclefleetmanagement.repository.car.OwnerRepository;
import my.project.vehiclefleetmanagement.repository.car.ResponsibleRepository;
import my.project.vehiclefleetmanagement.service.car.ResponsibleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResponsibleServiceImpl implements ResponsibleService {

    private final ResponsibleRepository responsibleRepository;
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;

    public ResponsibleServiceImpl(ResponsibleRepository responsibleRepository, OwnerRepository ownerRepository, ModelMapper modelMapper) {
        this.responsibleRepository = responsibleRepository;
        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createResponsible(CarPersonCreateDTO carPersonCreateDTO) {
        Optional<CarPerson> carPersonOptional = this.responsibleRepository.findByName( carPersonCreateDTO.getName());
        Optional<Owner> optionalOwner = this.ownerRepository.findById( carPersonCreateDTO.getOwner());

        if (carPersonOptional.isPresent() || optionalOwner.isEmpty()) {
            return false;
        }

        CarPerson mappedEntity = modelMapper.map(carPersonCreateDTO, CarPerson.class);
        mappedEntity.setOwner(optionalOwner.get());
        this.responsibleRepository.save(mappedEntity);
        return true;
    }

    @Override
    public List<CarPersonDTO> getAllResponsible(Long id) {
        List<CarPerson> carPersonList=  this.ownerRepository.findById( id).get().getDrivers();
        List<CarPersonDTO> carPersonDTOS = new ArrayList<>();
        for (CarPerson carPerson : carPersonList) {
            carPersonDTOS.add(modelMapper.map(carPerson, CarPersonDTO.class));
        }
        return carPersonDTOS;
    }

    @Override
    public CarPersonDTO getResponsibleById(Long id) {
        Optional<CarPerson> carPersonOptional = this.responsibleRepository.findById(id);
        return carPersonOptional.map(carPerson -> modelMapper.map(carPerson, CarPersonDTO.class)).orElse(null);
    }

    @Override
    public void deleteResponsible(Long id) {
        this.responsibleRepository.deleteById(id);
    }

    @Override
    public boolean updateResponsible(Long id, CarPersonEditDTO carPersonEditDTO) {
        Optional<CarPerson> carPersonOptional = this.responsibleRepository.findById(carPersonEditDTO.getId());
        if (carPersonOptional.isEmpty()) {
            return false;
        }

        CarPerson mappedEntity = modelMapper.map(carPersonEditDTO, CarPerson.class);
        this.responsibleRepository.save(mappedEntity);

        return true;
    }
}
