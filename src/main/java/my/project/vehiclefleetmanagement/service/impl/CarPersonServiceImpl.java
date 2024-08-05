package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonEditDTO;
import my.project.vehiclefleetmanagement.model.entity.car.CarPerson;
import my.project.vehiclefleetmanagement.repository.CarPersonRepository;
import my.project.vehiclefleetmanagement.service.CarPersonService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarPersonServiceImpl implements CarPersonService {

    private final CarPersonRepository carPersonRepository;
    private final ModelMapper modelMapper;

    public CarPersonServiceImpl(CarPersonRepository carPersonRepository, ModelMapper modelMapper) {
        this.carPersonRepository = carPersonRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createCarPerson(CarPersonCreateDTO carPersonCreateDTO) {
        Optional<CarPerson> carPersonOptional = this.carPersonRepository.findByPhoneNumber( carPersonCreateDTO.getPhoneNumber());

        if (carPersonOptional.isPresent() ) {
            throw new AppException(
                    String.format("Person with phone number %s is already exists!",carPersonCreateDTO.getPhoneNumber()),
                    HttpStatus.BAD_REQUEST);
        }

        CarPerson mappedEntity = modelMapper.map(carPersonCreateDTO, CarPerson.class);
        this.carPersonRepository.save(mappedEntity);
        throw new AppException("Person successfully created", HttpStatus.OK);
    }

    @Override
    public List<CarPersonDTO> getAllCarPerson() {
        List<CarPerson> carPersonList=  this.carPersonRepository.findAll( );
        List<CarPersonDTO> carPersonDTOS = new ArrayList<>();
        for (CarPerson carPerson : carPersonList) {
            carPersonDTOS.add(modelMapper.map(carPerson, CarPersonDTO.class));
        }
        return carPersonDTOS;
    }

    @Override
    public CarPersonDTO getCarPersonById(Long id) {
        Optional<CarPerson> carPersonOptional = this.carPersonRepository.findById(id);
        if (carPersonOptional.isEmpty()) {
            throw new AppException("Person is not found!", HttpStatus.NOT_FOUND);
        }

        return carPersonOptional.map(carPerson -> modelMapper.map(carPerson, CarPersonDTO.class))
                .orElseThrow(() -> new AppException("Unknown error", HttpStatus.UNPROCESSABLE_ENTITY));}

    @Override
    public void deleteCarPerson(Long id) {
        Optional<CarPerson> carPersonOptional = this.carPersonRepository.findById(id);
        if (carPersonOptional.isEmpty()) {
            throw new AppException("Person is not found!", HttpStatus.NOT_FOUND);
        }

        this.carPersonRepository.deleteById(id);
        throw new AppException("Person successfully deleted!", HttpStatus.OK);
    }

    @Override
    public void updateCarPerson(Long id, CarPersonEditDTO carPersonEditDTO) {
        Optional<CarPerson> carPersonOptional = this.carPersonRepository.findById(id);
        if (carPersonOptional.isEmpty()) {
            throw new AppException("Person is not found!", HttpStatus.NOT_FOUND);
        }

        Optional<CarPerson> personByPhoneNumber = this.carPersonRepository.findByPhoneNumber(carPersonEditDTO.getPhoneNumber());
        if (!Objects.equals(carPersonEditDTO.getPhoneNumber(), carPersonOptional.get().getPhoneNumber()) && personByPhoneNumber.isPresent()) {
            throw new AppException(
                    String.format("Person with phone number %s is already exists!",carPersonEditDTO.getPhoneNumber()),
                    HttpStatus.BAD_REQUEST);
        }

        CarPerson mappedEntity = modelMapper.map(carPersonEditDTO, CarPerson.class);
        this.carPersonRepository.save(mappedEntity);
        throw new AppException("Person successfully updated!", HttpStatus.OK);
    }
}
