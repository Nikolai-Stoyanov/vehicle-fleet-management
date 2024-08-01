package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.car.*;
import my.project.vehiclefleetmanagement.model.dtos.user.UserDto;
import my.project.vehiclefleetmanagement.model.entity.car.CarRecord;
import my.project.vehiclefleetmanagement.model.entity.car.RegistrationCertificateData;
import my.project.vehiclefleetmanagement.model.entity.declaration.Declaration;
import my.project.vehiclefleetmanagement.model.enums.VehicleTypeEnum;
import my.project.vehiclefleetmanagement.repository.CarRecordRepository;
import my.project.vehiclefleetmanagement.repository.DeclarationRepository;
import my.project.vehiclefleetmanagement.repository.RegistrationCertificateDataRepository;
import my.project.vehiclefleetmanagement.service.CarRecordService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CarRecordServiceImpl implements CarRecordService {
    private final RegistrationCertificateDataRepository registrationCertificateDataRepository;
    private final CarRecordRepository carRecordRepository;
    private final DeclarationRepository declarationRepository;
    private final ModelMapper modelMapper;

    public CarRecordServiceImpl(RegistrationCertificateDataRepository registrationCertificateDataRepository,
                                CarRecordRepository carRecordRepository, DeclarationRepository declarationRepository, ModelMapper modelMapper) {
        this.registrationCertificateDataRepository = registrationCertificateDataRepository;
        this.carRecordRepository = carRecordRepository;
        this.declarationRepository = declarationRepository;
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
        mappedEntity.setCreatedBy(getCurrentUserName());
        mappedEntity.setCreatedAt(LocalDate.now());

        this.carRecordRepository.save(mappedEntity);
        throw new AppException("Car record successfully created!", HttpStatus.OK);
    }

    protected String getCurrentUserName() {
        return   ((UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
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

        return modelMapper.map(carRecordOptional, CarRecordDTO.class);
    }

    @Override
    public void deleteCarRecord(Long id) {
        Optional<CarRecord> carRecordOptional = this.carRecordRepository.findById(id);

        if (carRecordOptional.isEmpty()) {
            throw new AppException("Car record is not found!", HttpStatus.NOT_FOUND);
        }
        this.carRecordRepository.deleteById(id);
        throw new AppException("Car record successfully deleted!", HttpStatus.OK);
    }

    @Override
    public boolean updateCarRecord(Long id, CarRecordEditDTO carPersonEditDTO) {
        Optional<CarRecord> carRecordOptional = this.carRecordRepository.findById(id);
        if (carRecordOptional.isEmpty()) {
            throw new AppException("Car record is not found!", HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(carRecordOptional.get().getRegistrationCertificateData().getRegistrationNumber(),
                carPersonEditDTO.getRegistrationCertificateData().getRegistrationNumber())) {
            throw new AppException("The registration number cannot be changed!", HttpStatus.BAD_REQUEST);
        }

        CarRecord editedCarRecord = carRecordOptional.get();
        modelMapper.map(carPersonEditDTO, editedCarRecord);
        editedCarRecord.setUpdatedBy(getCurrentUserName());
        editedCarRecord.setUpdatedAt(LocalDate.now());

        this.carRecordRepository.save(editedCarRecord);

        throw new AppException("Car record successfully updated!", HttpStatus.OK);
    }

    @Override
    public List<RegistrationNumberDTO> getAllRegistrationNumber() {
        List<RegistrationNumberDTO> registrationList = new ArrayList<>();

        List<RegistrationCertificateData> dataList = this.registrationCertificateDataRepository.findAll();

        for (RegistrationCertificateData data : dataList) {
            registrationList.add(new RegistrationNumberDTO(Math.toIntExact(data.getId()), data.getRegistrationNumber()));
        }
        return registrationList;
    }

    @Override
    public CarRecordInfoDTO getCarRecordInfoByRegistrationCertificateDataId(Long id) {
        Optional<RegistrationCertificateData> data = this.registrationCertificateDataRepository.findById(id);
        if (data.isEmpty()) {
            throw new AppException("Data is not found!", HttpStatus.NOT_FOUND);
        }
        CarRecord carRecord = this.carRecordRepository.findByRegistrationCertificateData(data.get());

        CarRecordInfoDTO carRecordInfoDTO = new CarRecordInfoDTO();
        carRecordInfoDTO.setRegistrationNumber(data.get().getRegistrationNumber());
        carRecordInfoDTO.setResponsible(carRecord.getResponsible().getFullName());
        carRecordInfoDTO.setFuelType(String.valueOf(carRecord.getFuelType()));

        List<Declaration> declarationList = this.declarationRepository.findAll().stream()
                .filter(d ->
                        Objects.equals(d.getCarRecord().getRegistrationCertificateData().getRegistrationNumber(),
                                data.get().getRegistrationNumber()))
                .sorted(Comparator.comparing(Declaration::getDate))
                .toList();

        if (!declarationList.isEmpty()){
            carRecordInfoDTO.setLastMileage(declarationList.get(declarationList.size() - 1).getNewMileage());
        }else {
            carRecordInfoDTO.setLastMileage(carRecord.getTotalMileage());
        }


        return carRecordInfoDTO;
    }

}
