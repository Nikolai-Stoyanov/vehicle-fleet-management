package my.project.vehiclefleetmanagement.service.nomenclatures.impl;

import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandListDTO;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarBrand;
import my.project.vehiclefleetmanagement.repository.nomenclatures.CarBrandRepository;
import my.project.vehiclefleetmanagement.service.nomenclatures.CarBrandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarBrandServiceImpl implements CarBrandService {
    private final CarBrandRepository carBrandRepository;
    private final ModelMapper modelMapper;

    public CarBrandServiceImpl(CarBrandRepository carBrandRepository, ModelMapper modelMapper) {
        this.carBrandRepository = carBrandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createBrand(CarBrandCreateDTO carBrandCreateDTO) {
        Optional<CarBrand> carBrandOptional = this.carBrandRepository.findByName( carBrandCreateDTO.getName());

        if (carBrandOptional.isPresent()) {
            return false;
        }

        CarBrand mappedEntity = modelMapper.map(carBrandCreateDTO, CarBrand.class);
        this.carBrandRepository.save(mappedEntity);
        return true;
    }

    @Override
    public List<CarBrandListDTO> getAllBrands() {
      List<CarBrand> carBrandList=  carBrandRepository.findAll();
      List<CarBrandListDTO> carBrandListDTOs = new ArrayList<>();
      for (CarBrand carBrand : carBrandList) {
          carBrandListDTOs.add(modelMapper.map(carBrand, CarBrandListDTO.class));
      }
        return carBrandListDTOs;
    }

    @Override
    public CarBrandDTO getBrandById(Long id) {
        Optional<CarBrand> carBrandOptional = this.carBrandRepository.findById(id);
        return carBrandOptional.map(carBrand -> modelMapper.map(carBrand, CarBrandDTO.class)).orElse(null);
    }

    @Override
    public void deleteBrand(Long id) {
        this.carBrandRepository.deleteById(id);
    }

    @Override
    public boolean updateBrand(Long id, CarBrandEditDTO carBrandEditDTO) {
        Optional<CarBrand> carBrandOptional = this.carBrandRepository.findById( carBrandEditDTO.getId());
        if (carBrandOptional.isEmpty()) {
            return false;
        }
        CarBrand mappedEntity = modelMapper.map(carBrandEditDTO, CarBrand.class);
        this.carBrandRepository.save(mappedEntity);

        return true;
    }
}
