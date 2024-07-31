package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelListDTO;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarBrand;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarModel;
import my.project.vehiclefleetmanagement.repository.CarBrandRepository;
import my.project.vehiclefleetmanagement.repository.CarModelRepository;
import my.project.vehiclefleetmanagement.service.CarModelService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarModelServiceImpl implements CarModelService {
    private final CarModelRepository carModelRepository;
    private final ModelMapper modelMapper;
    private final CarBrandRepository carBrandRepository;

    public CarModelServiceImpl(CarModelRepository carModelRepository, ModelMapper modelMapper, CarBrandRepository carBrandRepository) {
        this.carModelRepository = carModelRepository;
        this.modelMapper = modelMapper;
        this.carBrandRepository = carBrandRepository;
    }

    @Override
    public List<CarModelListDTO> getAllModels() {
        List<CarModel> carModelList=  carModelRepository.findAll();

        List<CarModelListDTO> carModelListDTOs = new ArrayList<>();
        for (CarModel carModel : carModelList) {
            CarModelListDTO model=modelMapper.map(carModel, CarModelListDTO.class);
            CarBrand carBrand=this.carBrandRepository.findById(carModel.getBrand().getId()).get();
            model.setBrandName(carBrand.getName());
            carModelListDTOs.add(model);
        }

        return carModelListDTOs;
    }

    @Override
    public void createModel(CarModelCreateDTO carModelCreateDTO) {
        Optional<CarModel> carModelOptional = this.carModelRepository.findByName( carModelCreateDTO.getName());
        Optional<CarBrand> carBrandOptional = this.carBrandRepository.findById( carModelCreateDTO.getBrand());

        if (carModelOptional.isPresent()) {
            throw new AppException("Car model already exists", HttpStatus.BAD_REQUEST);
        }
        if (carBrandOptional.isEmpty()) {
            throw new AppException("Car brand not found", HttpStatus.BAD_REQUEST);
        }

        CarModel mappedEntity = modelMapper.map(carModelCreateDTO, CarModel.class);
        mappedEntity.setBrand(carBrandOptional.get());
        this.carModelRepository.save(mappedEntity);
        throw new AppException("Car model successfully created", HttpStatus.OK);
    }

    @Override
    public void updateModel(Long id, CarModelEditDTO carModelEditDTO) {
        Optional<CarModel> carModelOptional = this.carModelRepository.findById( id);
        Optional<CarBrand> carBrandOptional = this.carBrandRepository.findById( carModelEditDTO.getBrand());
        if (carModelOptional.isEmpty()) {
            throw new AppException("Car model is not found!", HttpStatus.NOT_FOUND);
        }
        if (carBrandOptional.isEmpty()) {
            throw new AppException("Car brand not found!", HttpStatus.BAD_REQUEST);
        }
        CarModel mappedEntity = modelMapper.map(carModelEditDTO, CarModel.class);
        mappedEntity.setBrand(carBrandOptional.get());
        this.carModelRepository.save(mappedEntity);

        throw new AppException("Car model successfully updated!", HttpStatus.OK);
    }

    @Override
    public CarModelDTO getModelById(Long id) {
        Optional<CarModel> carModelOptional = this.carModelRepository.findById( id);
        if (carModelOptional.isEmpty()) {
            throw new AppException("Car model is not found!", HttpStatus.NOT_FOUND);
        }
        return carModelOptional.map(carModel -> modelMapper.map(carModel, CarModelDTO.class))
                .orElseThrow(() -> new AppException("Unknown error", HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Override
    public void deleteModel(Long id) {
        Optional<CarModel> carModelOptional = this.carModelRepository.findById( id);
        if (carModelOptional.isEmpty()) {
            throw new AppException("Car model is not found!", HttpStatus.NOT_FOUND);
        }
        this.carModelRepository.deleteById(id);
        throw new AppException("Car model successfully deleted!", HttpStatus.OK);
    }
}
