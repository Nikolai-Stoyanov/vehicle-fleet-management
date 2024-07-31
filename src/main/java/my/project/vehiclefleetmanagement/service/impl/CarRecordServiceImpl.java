package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.car.*;
import my.project.vehiclefleetmanagement.model.entity.car.CarPerson;
import my.project.vehiclefleetmanagement.model.entity.car.CarRecord;
import my.project.vehiclefleetmanagement.model.entity.car.RegistrationCertificateData;
import my.project.vehiclefleetmanagement.model.enums.VehicleTypeEnum;
import my.project.vehiclefleetmanagement.repository.CarRecordRepository;
import my.project.vehiclefleetmanagement.repository.RegistrationCertificateDataRepository;
import my.project.vehiclefleetmanagement.service.CarRecordService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarRecordServiceImpl implements CarRecordService {
    private final RegistrationCertificateDataRepository registrationCertificateDataRepository;
    private final CarRecordRepository carRecordRepository;
    private final ModelMapper modelMapper;

    public CarRecordServiceImpl(RegistrationCertificateDataRepository registrationCertificateDataRepository,
                                CarRecordRepository carRecordRepository, ModelMapper modelMapper) {
        this.registrationCertificateDataRepository = registrationCertificateDataRepository;
        this.carRecordRepository = carRecordRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createCarRecord(CarRecordCreateDTO carRecordCreateDTO) {
        Optional<RegistrationCertificateData> optionalData =
                this.registrationCertificateDataRepository
                        .findByRegistrationNumber(carRecordCreateDTO.getRegistrationCertificateData().getRegistrationNumber());

        if (optionalData.isEmpty()) {
            RegistrationCertificateData newData
                    = modelMapper.map(carRecordCreateDTO.getRegistrationCertificateData(), RegistrationCertificateData.class);
            newData.setFirstRegistration(LocalDate.parse(carRecordCreateDTO.getRegistrationCertificateData().getFirstRegistration()));
            newData.setVehicleType(
                    VehicleTypeEnum.valueOf(carRecordCreateDTO.getRegistrationCertificateData().getVehicleType()));
            registrationCertificateDataRepository.save(newData);

        } else {
            throw new AppException("Car record with this register number already exists!", HttpStatus.BAD_REQUEST);
        }
        RegistrationCertificateData registrationCertificateData = registrationCertificateDataRepository
                .findByRegistrationNumber(carRecordCreateDTO.getRegistrationCertificateData().getRegistrationNumber()).get();


        CarRecord mappedEntity = modelMapper.map(carRecordCreateDTO, CarRecord.class);
        mappedEntity.setRegistrationCertificateData(registrationCertificateData);
        mappedEntity.setCreatedBy("currentPrincipalName");
        mappedEntity.setCreatedAt(LocalDate.now());

        this.carRecordRepository.save(mappedEntity);
        return true;
    }

    @Override
    public List<CarRecordListDTO> getAllCarRecord() {
        List<CarRecord> carRecordList = this.carRecordRepository.findAll();
        List<CarRecordListDTO> carRecordListDTOS = new ArrayList<>();
        for (CarRecord carRecord : carRecordList) {
            CarRecordListDTO carRecordListDTO = new CarRecordListDTO();
            carRecordListDTO.setId(Math.toIntExact(carRecord.getId()));
            carRecordListDTO.setDescription(carRecord.getDescription());
            carRecordListDTO.setOwner(carRecord.getOwner());
            carRecordListDTO.setCarBrand(carRecord.getRegistrationCertificateData().getModel().getBrand().getName());
            carRecordListDTO.setCarModel(carRecord.getRegistrationCertificateData().getModel().getName());
            carRecordListDTO.setDrivingCategory(String.valueOf(carRecord.getDrivingCategory()));
            carRecordListDTO.setRegistrationNumber(carRecord.getRegistrationCertificateData().getRegistrationNumber());
            carRecordListDTO.setResponsible(carRecord.getResponsible().getFullName());
            carRecordListDTO.setStatus(carRecord.isStatus());

            carRecordListDTOS.add(carRecordListDTO);
        }
        return carRecordListDTOS;
    }

    @Override
    public CarRecordDTO getCarRecordById(Long id) {
        Optional<CarRecord> carRecordOptional = this.carRecordRepository.findById(id);

        if (carRecordOptional.isEmpty()) {
            throw new AppException("Car record is not found!", HttpStatus.NOT_FOUND);
        }
        CarRecordDTO carRecordDTO = modelMapper.map(carRecordOptional, CarRecordDTO.class);

        return carRecordDTO;
    }

    @Override
    public void deleteCarRecord(Long id) {
        this.carRecordRepository.deleteById(id);
    }

    @Override
    public boolean updateCarRecord(Long id, CarRecordEditDTO carPersonEditDTO) {
        Optional<CarRecord> carRecordOptional = this.carRecordRepository.findById(id);
        if (carRecordOptional.isEmpty()) {
            return false;
        }
        CarRecord editedCarRecord = carRecordOptional.get();
        modelMapper.map(carPersonEditDTO, editedCarRecord);
//        editedCarRecord.setDriver(modelMapper.map(carPersonEditDTO.getDriver(), CarPerson.class));
//        editedCarRecord.setResponsible(modelMapper.map(carPersonEditDTO.getResponsible(), CarPerson.class));
        editedCarRecord.setDriver(null);
        editedCarRecord.setResponsible(null);
        editedCarRecord.setUpdatedBy("currentPrincipalName");
        editedCarRecord.setUpdatedAt(LocalDate.now());

        this.carRecordRepository.save(editedCarRecord);

        return true;
    }
}
