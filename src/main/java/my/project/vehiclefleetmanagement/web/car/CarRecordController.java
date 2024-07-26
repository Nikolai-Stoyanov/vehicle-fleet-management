package my.project.vehiclefleetmanagement.web.car;

import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordEditDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.carRecord.CarRecordListDTO;
import my.project.vehiclefleetmanagement.service.car.CarRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carRecord")

public class CarRecordController {

    private final CarRecordService carRecordService;

    public CarRecordController(CarRecordService carRecordService) {
        this.carRecordService = carRecordService;
    }

    @GetMapping
    public ResponseEntity<List<CarRecordListDTO>> getAllCarRecords() {
        return ResponseEntity.ok(
                this.carRecordService.getAllCarRecord()
        );
    }

    @PostMapping
    public ResponseEntity<String> createCarRecord(@RequestBody CarRecordCreateDTO carPersonCreateDTO) {

        boolean success = this.carRecordService.createCarRecord(carPersonCreateDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Car record already exist!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCarRecord(@PathVariable("id") Long id,@RequestBody CarRecordEditDTO carPersonEditDTO) {

        boolean success = this.carRecordService.updateCarRecord(id, carPersonEditDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car record not found!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarRecordDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(this.carRecordService.getCarRecordById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        this.carRecordService.deleteCarRecord(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
