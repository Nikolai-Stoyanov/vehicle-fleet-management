package my.project.vehiclefleetmanagement.service.car.impl;

import my.project.vehiclefleetmanagement.model.dtos.car.fuelCard.FuelCardCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.fuelCard.FuelCardDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.fuelCard.FuelCardEditDTO;
import my.project.vehiclefleetmanagement.model.entity.car.CarRecord;
import my.project.vehiclefleetmanagement.model.entity.car.FuelCard;
import my.project.vehiclefleetmanagement.repository.car.CarRecordRepository;
import my.project.vehiclefleetmanagement.repository.car.FuelCardRepository;
import my.project.vehiclefleetmanagement.service.car.FuelCardService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FuelCardServiceImpl implements FuelCardService {

    private final FuelCardRepository fuelCardRepository;
    private final CarRecordRepository carRecordRepository;
    private final ModelMapper modelMapper;

    public FuelCardServiceImpl(FuelCardRepository fuelCardRepository, CarRecordRepository carRecordRepository, ModelMapper modelMapper) {
        this.fuelCardRepository = fuelCardRepository;
        this.carRecordRepository = carRecordRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createFuelCard(FuelCardCreateDTO fuelCardCreateDTO) {
        Optional<FuelCard> fuelCardOptional = this.fuelCardRepository.findByCardNumber(fuelCardCreateDTO.getCardNumber());
        Optional<CarRecord> carRecordOptional = this.carRecordRepository.findById(fuelCardCreateDTO.getCarRecord());

        if (fuelCardOptional.isPresent() || carRecordOptional.isEmpty()) {
            return false;
        }

        FuelCard mappedEntity = modelMapper.map(fuelCardCreateDTO, FuelCard.class);
        mappedEntity.setCarRecord(carRecordOptional.get());
        this.fuelCardRepository.save(mappedEntity);
        return true;
    }

    @Override
    public List<FuelCardDTO> getAllFuelCards(Long id) {
        List<FuelCard> fuelCardList = this.carRecordRepository.findById(id).get().getFuelCardList();
        List<FuelCardDTO> fuelCardDTOS = new ArrayList<>();
        for (FuelCard fuelCard : fuelCardList) {
            fuelCardDTOS.add(modelMapper.map(fuelCard, FuelCardDTO.class));
        }
        return fuelCardDTOS;
    }

    @Override
    public FuelCardDTO getFuelCardById(Long id) {
        Optional<FuelCard> fuelCardOptional = this.fuelCardRepository.findById(id);
        return fuelCardOptional.map(fuelCard -> modelMapper.map(fuelCard, FuelCardDTO.class)).orElse(null);
    }

    @Override
    public void deleteFuelCard(Long id) {
        this.fuelCardRepository.deleteById(id);
    }

    @Override
    public boolean updateFuelCard(Long id, FuelCardEditDTO fuelCardEditDTO) {
        Optional<FuelCard> fuelCardOptional = this.fuelCardRepository.findById(fuelCardEditDTO.getId());
        if (fuelCardOptional.isEmpty()) {
            return false;
        }

        FuelCard mappedEntity = modelMapper.map(fuelCardEditDTO, FuelCard.class);
        this.fuelCardRepository.save(mappedEntity);

        return true;
    }
}
