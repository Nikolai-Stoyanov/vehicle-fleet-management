package my.project.vehiclefleetmanagement.service.car.impl;

import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonEditDTO;
import my.project.vehiclefleetmanagement.model.entity.car.CarPerson;
import my.project.vehiclefleetmanagement.model.entity.car.Owner;
import my.project.vehiclefleetmanagement.repository.car.DriverRepository;
import my.project.vehiclefleetmanagement.repository.car.OwnerRepository;
import my.project.vehiclefleetmanagement.service.car.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;

    public DriverServiceImpl(DriverRepository driverRepository, OwnerRepository ownerRepository, ModelMapper modelMapper) {
        this.driverRepository = driverRepository;
        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createDriver(CarPersonCreateDTO carPersonCreateDTO) {
        Optional<CarPerson> carPersonOptional = this.driverRepository.findByName( carPersonCreateDTO.getName());
        Optional<Owner> optionalOwner = this.ownerRepository.findById( carPersonCreateDTO.getOwner());

        if (carPersonOptional.isPresent() || optionalOwner.isEmpty()) {
            return false;
        }

        CarPerson mappedEntity = modelMapper.map(carPersonCreateDTO, CarPerson.class);
        mappedEntity.setOwner(optionalOwner.get());
        this.driverRepository.save(mappedEntity);
        return true;
    }

    @Override
    public List<CarPersonDTO> getAllDriver(Long id) {
        List<CarPerson> carPersonList=  this.ownerRepository.findById( id).get().getDrivers();
        List<CarPersonDTO> carPersonDTOS = new ArrayList<>();
        for (CarPerson carPerson : carPersonList) {
            carPersonDTOS.add(modelMapper.map(carPerson, CarPersonDTO.class));
        }
        return carPersonDTOS;
    }

    @Override
    public CarPersonDTO getDriverById(Long id) {
        Optional<CarPerson> carPersonOptional = this.driverRepository.findById(id);
        return carPersonOptional.map(carPerson -> modelMapper.map(carPerson, CarPersonDTO.class)).orElse(null);
    }

    @Override
    public void deleteDriver(Long id) {
        this.driverRepository.deleteById(id);
    }

    @Override
    public boolean updateDriver(Long id, CarPersonEditDTO carPersonEditDTO) {
        Optional<CarPerson> carPersonOptional = this.driverRepository.findById(carPersonEditDTO.getId());
        if (carPersonOptional.isEmpty()) {
            return false;
        }

        CarPerson mappedEntity = modelMapper.map(carPersonEditDTO, CarPerson.class);
        this.driverRepository.save(mappedEntity);

        return true;
    }
}
