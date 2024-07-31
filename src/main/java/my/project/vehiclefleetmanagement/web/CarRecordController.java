package my.project.vehiclefleetmanagement.web;

import my.project.vehiclefleetmanagement.model.dtos.car.*;
import my.project.vehiclefleetmanagement.service.CarRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carRecord")
@CrossOrigin
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
        this.carRecordService.createCarRecord(carPersonCreateDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCarRecord(@PathVariable("id") Long id,@RequestBody CarRecordEditDTO carPersonEditDTO) {
        this.carRecordService.updateCarRecord(id, carPersonEditDTO);
        return ResponseEntity.noContent().build();
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

    @GetMapping("/registrationNumber")
    public ResponseEntity<List<RegistrationNumberDTO>> getById() {
        return ResponseEntity
                .ok(this.carRecordService.getAllRegistrationNumber());
    }

    @GetMapping("/registrationCertificateData/{id}")
    public ResponseEntity<CarRecordInfoDTO> getInfoById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(this.carRecordService.getCarRecordInfoByRegistrationCertificateDataId(id));
    }
}
