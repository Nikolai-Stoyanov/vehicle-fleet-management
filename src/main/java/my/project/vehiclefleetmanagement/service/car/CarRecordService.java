package my.project.vehiclefleetmanagement.service.car;

import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordListDTO;

import java.util.List;

public interface CarRecordService {

    boolean createCarRecord(CarRecordCreateDTO carRecordCreateDTO);

    List<CarRecordListDTO> getAllCarRecord();

    CarRecordDTO getCarRecordById(Long id);

    void deleteCarRecord(Long id);

    boolean updateCarRecord(Long id, CarRecordEditDTO carRecordEditDTO);
}
