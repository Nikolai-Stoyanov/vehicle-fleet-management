package my.project.vehiclefleetmanagement.service.nomenclatures.impl;

import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carModel.CarModelListDTO;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarBrand;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarModel;
import my.project.vehiclefleetmanagement.repository.nomenclatures.CarBrandRepository;
import my.project.vehiclefleetmanagement.repository.nomenclatures.CarModelRepository;
import my.project.vehiclefleetmanagement.service.nomenclatures.CarModelService;
import org.modelmapper.ModelMapper;
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
            model.setBrand(carBrand.getName());
            carModelListDTOs.add(model);
        }

        return carModelListDTOs;
    }

    @Override
    public boolean createModel(CarModelCreateDTO carModelCreateDTO) {
        Optional<CarModel> carModelOptional = this.carModelRepository.findByName( carModelCreateDTO.getName());
        Optional<CarBrand> carBrandOptional = this.carBrandRepository.findById( carModelCreateDTO.getBrand());

        if (carModelOptional.isPresent() || carBrandOptional.isEmpty()) {
            return false;
        }

        CarModel mappedEntity = modelMapper.map(carModelCreateDTO, CarModel.class);
        mappedEntity.setBrand(carBrandOptional.get());
        this.carModelRepository.save(mappedEntity);
        return true;
    }

    @Override
    public boolean updateModel(Long id, CarModelEditDTO carModelEditDTO) {
        Optional<CarModel> carModelOptional = this.carModelRepository.findById( carModelEditDTO.getId());
        Optional<CarBrand> carBrandOptional = this.carBrandRepository.findById( carModelEditDTO.getBrand());
        if (carModelOptional.isEmpty() || carBrandOptional.isEmpty()) {
            return false;
        }
        CarModel mappedEntity = modelMapper.map(carModelEditDTO, CarModel.class);
        mappedEntity.setBrand(carBrandOptional.get());
        this.carModelRepository.save(mappedEntity);

        return true;
    }

    @Override
    public CarModelDTO getModelById(Long id) {
        Optional<CarModel> carModelOptional = this.carModelRepository.findById(id);
        return carModelOptional.map(carModel -> modelMapper.map(carModel, CarModelDTO.class)).orElse(null);
    }

    @Override
    public void deleteModel(Long id) {
        this.carModelRepository.deleteById(id);
    }
}
