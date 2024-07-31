package my.project.vehiclefleetmanagement.service;

import my.project.vehiclefleetmanagement.model.dtos.car.*;

import java.util.List;

public interface CarRecordService {

    boolean createCarRecord(CarRecordCreateDTO carRecordCreateDTO);

    List<CarRecordListDTO> getAllCarRecord();

    CarRecordDTO getCarRecordById(Long id);

    void deleteCarRecord(Long id);

    boolean updateCarRecord(Long id, CarRecordEditDTO carRecordEditDTO);

    List<RegistrationNumberDTO> getAllRegistrationNumber();

    CarRecordInfoDTO getCarRecordInfoByRegistrationCertificateDataId(Long id);
}
