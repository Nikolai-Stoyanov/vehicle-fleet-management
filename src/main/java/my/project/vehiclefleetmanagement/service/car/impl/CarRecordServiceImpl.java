package my.project.vehiclefleetmanagement.service.car.impl;

import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordListDTO;
import my.project.vehiclefleetmanagement.model.entity.car.CarRecord;
import my.project.vehiclefleetmanagement.repository.car.CarRecordRepository;
import my.project.vehiclefleetmanagement.service.car.CarRecordService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarRecordServiceImpl implements CarRecordService {

    private final CarRecordRepository carRecordRepository;
    private final ModelMapper modelMapper;

    public CarRecordServiceImpl(CarRecordRepository carRecordRepository, ModelMapper modelMapper) {
        this.carRecordRepository = carRecordRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createCarRecord(CarRecordCreateDTO carRecordCreateDTO) {

        CarRecord mappedEntity = modelMapper.map(carRecordCreateDTO, CarRecord.class);
        this.carRecordRepository.save(mappedEntity);
        return true;
    }

    @Override
    public List<CarRecordListDTO> getAllCarRecord() {
        List<CarRecord> carRecordList=  this.carRecordRepository.findAll();
        List<CarRecordListDTO> carRecordListDTOS = new ArrayList<>();
        for (CarRecord carRecord : carRecordList) {
            CarRecordListDTO carRecordListDTO=  modelMapper.map(carRecord, CarRecordListDTO.class);
            carRecordListDTO.setCarBrand(carRecord.getRegistrationCertificateData().getModel().getBrand().getName());
            carRecordListDTO.setCarModel(carRecord.getRegistrationCertificateData().getModel().getName());
            carRecordListDTO.setOwnerName(carRecord.getOwner().getName());
            carRecordListDTO.setResponsibleName(carRecord.getOwner().getPersonResponsible().getName());
            carRecordListDTO.setDrivingCategory(String.valueOf(carRecord.getDrivingCategory()));
            carRecordListDTO.setRegistrationNumber(carRecord.getRegistrationCertificateData().getRegistrationNumbers().get(0).getRegistration());


            carRecordListDTOS.add(carRecordListDTO);
        }
        return carRecordListDTOS;
    }

    @Override
    public CarRecordDTO getCarRecordById(Long id) {
        Optional<CarRecord> carRecordOptional = this.carRecordRepository.findById(id);
        return carRecordOptional.map(carRecord -> modelMapper.map(carRecord, CarRecordDTO.class)).orElse(null);
    }

    @Override
    public void deleteCarRecord(Long id) {
        this.carRecordRepository.deleteById(id);
    }

    @Override
    public boolean updateCarRecord(Long id, CarRecordEditDTO carPersonEditDTO) {
        Optional<CarRecord> carRecordOptional = this.carRecordRepository.findById(carPersonEditDTO.getId());
        if (carRecordOptional.isEmpty()) {
            return false;
        }

        CarRecord mappedEntity = modelMapper.map(carPersonEditDTO, CarRecord.class);
        this.carRecordRepository.save(mappedEntity);

        return true;
    }
}
