package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.config.Messages;
import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.carBrand.CarBrandListDTO;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarBrand;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.CarModel;
import my.project.vehiclefleetmanagement.repository.CarBrandRepository;
import my.project.vehiclefleetmanagement.service.CarBrandService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public void createBrand(CarBrandCreateDTO carBrandCreateDTO) {
        Optional<CarBrand> carBrandOptional = this.carBrandRepository.findByName( carBrandCreateDTO.getName());

        if (carBrandOptional.isPresent()) {
            throw new AppException(Messages.getLocaleMessage("Car_brand_already_exists!"), HttpStatus.BAD_REQUEST);
        }

        CarBrand mappedEntity = modelMapper.map(carBrandCreateDTO, CarBrand.class);
        this.carBrandRepository.save(mappedEntity);
        throw new AppException(Messages.getLocaleMessage("Car_brand_successfully_created!"), HttpStatus.OK);
    }

    @Override
    public List<CarBrandListDTO> getAllBrands() {
      List<CarBrand> carBrandList=  carBrandRepository.findAll();
      List<CarBrandListDTO> carBrandListDTOs = new ArrayList<>();
      for (CarBrand carBrand : carBrandList) {
          CarBrandListDTO carBrandListDTO = modelMapper.map(carBrand, CarBrandListDTO.class);

          List<String> modelsList = new ArrayList<>();
          for (CarModel carModel : carBrand.getModels()) {
              modelsList.add(carModel.getName());
          }
          carBrandListDTO.setModels(modelsList);

          carBrandListDTOs.add(carBrandListDTO);
      }
        return carBrandListDTOs;
    }

    @Override
    public CarBrandDTO getBrandById(Long id) {
        Optional<CarBrand> carBrandOptional = this.carBrandRepository.findById(id);
        if (carBrandOptional.isEmpty()) {
            throw new AppException(Messages.getLocaleMessage("Car_brand_is_not_found!"), HttpStatus.NOT_FOUND);
        }
        return carBrandOptional
                .map(carBrand -> modelMapper.map(carBrand, CarBrandDTO.class))
                .orElseThrow(() -> new AppException("Unknown error", HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Override
    public void deleteBrand(Long id) {
        Optional<CarBrand> carBrandOptional = this.carBrandRepository.findById(id);
        if (carBrandOptional.isEmpty()) {
            throw new AppException(Messages.getLocaleMessage("Car_brand_is_not_found!"), HttpStatus.NOT_FOUND);
        }
        this.carBrandRepository.deleteById(id);
        throw new AppException(Messages.getLocaleMessage("Car_brand_successfully_deleted!"), HttpStatus.OK);
    }

    @Override
    public void updateBrand(Long id, CarBrandEditDTO carBrandEditDTO) {
        Optional<CarBrand> carBrandOptional = this.carBrandRepository.findById(carBrandEditDTO.getId());
        if (carBrandOptional.isEmpty()) {
            throw new AppException(Messages.getLocaleMessage("Car_brand_is_not_found!"), HttpStatus.NOT_FOUND);
        }

        Optional<CarBrand> brandByName = this.carBrandRepository.findByName(carBrandEditDTO.getName());
        if (!Objects.equals(carBrandEditDTO.getName(), carBrandOptional.get().getName()) && brandByName.isPresent()) {
            throw new AppException(
                    Messages.getLocaleMessage("Car_brand_with_this_name_already_exists!"),
                    HttpStatus.BAD_REQUEST);
        }

        CarBrand mappedEntity = modelMapper.map(carBrandEditDTO, CarBrand.class);
        mappedEntity.setModels(carBrandOptional.get().getModels());
        this.carBrandRepository.save(mappedEntity);

        throw new AppException(Messages.getLocaleMessage("Car_brand_successfully_updated!"), HttpStatus.OK);
    }
}
