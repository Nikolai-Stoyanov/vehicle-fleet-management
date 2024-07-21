package my.project.vehiclefleetmanagement.service.car.impl;

import my.project.vehiclefleetmanagement.model.dtos.car.registrationNumber.RegistrationNumberCreateDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.registrationNumber.RegistrationNumberDTO;
import my.project.vehiclefleetmanagement.model.dtos.car.registrationNumber.RegistrationNumberEditDTO;
import my.project.vehiclefleetmanagement.model.entity.car.RegistrationCertificateData;
import my.project.vehiclefleetmanagement.model.entity.car.RegistrationNumber;
import my.project.vehiclefleetmanagement.repository.car.RegistrationCertificateDataRepository;
import my.project.vehiclefleetmanagement.repository.car.RegistrationNumberRepository;
import my.project.vehiclefleetmanagement.service.car.RegistrationNumberService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationNumberServiceImpl implements RegistrationNumberService {

    private final RegistrationNumberRepository registrationNumberRepository;
    private final RegistrationCertificateDataRepository registrationCertificateDataRepository;
    private final ModelMapper modelMapper;

    public RegistrationNumberServiceImpl(RegistrationNumberRepository registrationNumberRepository,
                                         RegistrationCertificateDataRepository registrationCertificateDataRepository,
                                         ModelMapper modelMapper) {
        this.registrationNumberRepository = registrationNumberRepository;
        this.registrationCertificateDataRepository = registrationCertificateDataRepository;

        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createRegistrationNumber(RegistrationNumberCreateDTO registrationNumberCreateDTO) {
        Optional<RegistrationNumber> registrationNumberOptional =
                this.registrationNumberRepository.findByRegistration(registrationNumberCreateDTO.getRegistration());
        Optional<RegistrationCertificateData> optional =
                this.registrationCertificateDataRepository
                        .findById(registrationNumberCreateDTO.getRegistrationCertificateData());

        if (registrationNumberOptional.isPresent() || optional.isEmpty()) {
            return false;
        }

        RegistrationNumber mappedEntity = modelMapper.map(registrationNumberCreateDTO, RegistrationNumber.class);
        mappedEntity.setRegistrationCertificateData(optional.get());
        this.registrationNumberRepository.save(mappedEntity);
        return true;
    }

    @Override
    public List<RegistrationNumberDTO> getAllRegistrationNumber(Long id) {
        List<RegistrationNumber> registrationNumberList =
                this.registrationCertificateDataRepository.findById(id).get().getRegistrationNumbers();
        List<RegistrationNumberDTO> registrationNumberDTOS = new ArrayList<>();
        for (RegistrationNumber registrationNumber : registrationNumberList) {
            registrationNumberDTOS.add(modelMapper.map(registrationNumber, RegistrationNumberDTO.class));
        }
        return registrationNumberDTOS;
    }

    @Override
    public RegistrationNumberDTO getRegistrationNumberById(Long id) {
        Optional<RegistrationNumber> registrationNumberOptional = this.registrationNumberRepository.findById(id);
        return registrationNumberOptional
                .map(registrationNumber -> modelMapper.map(registrationNumber, RegistrationNumberDTO.class))
                .orElse(null);
    }

    @Override
    public void deleteRegistrationNumber(Long id) {
        this.registrationNumberRepository.deleteById(id);
    }

    @Override
    public boolean updateRegistrationNumber(Long id, RegistrationNumberEditDTO registrationNumberEditDTO) {
        Optional<RegistrationNumber> registrationNumberOptional =
                this.registrationNumberRepository.findById(registrationNumberEditDTO.getId());
        if (registrationNumberOptional.isEmpty()) {
            return false;
        }

        RegistrationNumber mappedEntity = modelMapper.map(registrationNumberEditDTO, RegistrationNumber.class);
        this.registrationNumberRepository.save(mappedEntity);
        return true;
    }
}
