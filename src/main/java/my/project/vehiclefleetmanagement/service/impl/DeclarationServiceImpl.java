package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.model.dtos.declaration.*;
import my.project.vehiclefleetmanagement.model.entity.car.CarRecord;
import my.project.vehiclefleetmanagement.model.entity.car.DeclarationFuel;
import my.project.vehiclefleetmanagement.model.entity.declaration.Declaration;
import my.project.vehiclefleetmanagement.repository.CarRecordRepository;
import my.project.vehiclefleetmanagement.repository.DeclarationRepository;
import my.project.vehiclefleetmanagement.service.DeclarationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeclarationServiceImpl implements DeclarationService {
    private final DeclarationRepository declarationRepository;
    private final ModelMapper modelMapper;
    private final CarRecordRepository carRecordRepository;

    public DeclarationServiceImpl(DeclarationRepository declarationRepository, ModelMapper modelMapper, CarRecordRepository carRecordRepository) {
        this.declarationRepository = declarationRepository;
        this.modelMapper = modelMapper;
        this.carRecordRepository = carRecordRepository;
    }

    @Override
    public boolean createDeclaration(DeclarationCreateDTO declarationCreateDTO) {

        Optional<CarRecord> optionalCarRecord = this.carRecordRepository.findById(declarationCreateDTO.getCarRecordId());

        if (optionalCarRecord.isEmpty()) {
            return false;
        }

        Declaration mappedEntity = modelMapper.map(declarationCreateDTO, Declaration.class);
        mappedEntity.setCarRecord(optionalCarRecord.get());
        this.declarationRepository.save(mappedEntity);
        return true;
    }

    @Override
    public List<DeclarationListDTO> getAllDeclarations() {

        List<Declaration> declarationList = this.declarationRepository.findAll();

        List<DeclarationListDTO> declarationListDTOS = new ArrayList<>();
        for (Declaration declaration : declarationList) {
            DeclarationListDTO model = modelMapper.map(declaration, DeclarationListDTO.class);

            CarRecord carRecord = this.carRecordRepository.findById(declaration.getCarRecord().getId()).get();
            model.setRegistrationNumber(carRecord.getRegistrationCertificateData().getRegistrationNumbers().get(0).getRegistration());
            model.setResponsible(carRecord.getOwner().getPersonResponsible().getName());
            declarationListDTOS.add(model);
        }

        return declarationListDTOS;
    }

    @Override
    public DeclarationDTO getDeclarationById(Long id) {
        Optional<Declaration> declaration = this.declarationRepository.findById(id);
        if (declaration.isEmpty()) {
            return null;
        }
        DeclarationDTO declarationDTO = modelMapper.map(declaration, DeclarationDTO.class);

        CarRecord carRecord = declaration.get().getCarRecord();
        declarationDTO.setRegistrationNumber(carRecord.getRegistrationCertificateData().getRegistrationNumbers().get(0).getRegistration());
        declarationDTO.setResponsible(carRecord.getOwner().getPersonResponsible().getName());
        List<DeclarationFuelDTO> declarationFuelDTOS = new ArrayList<>();
        for (DeclarationFuel declarationFuel : declaration.get().getFuels()) {
            declarationFuelDTOS.add(modelMapper.map(declarationFuel, DeclarationFuelDTO.class));
        }
        declarationDTO.setFuelsList(declarationFuelDTOS);

        return declarationDTO;
    }

    @Override
    public void deleteDeclaration(Long id) {
        this.declarationRepository.deleteById(id);
    }

    @Override
    public boolean updateDeclaration(Long id, DeclarationEditDTO declarationEditDTO) {
        Optional<Declaration> declaration = this.declarationRepository.findById(id);
        Optional<CarRecord> optionalCarRecord = this.carRecordRepository.findById(declarationEditDTO.getCarRecordId());

        if (declaration.isEmpty() || optionalCarRecord.isEmpty()) {
            return false;
        }

        Declaration mappedEntity = modelMapper.map(declarationEditDTO, Declaration.class);
        mappedEntity.setCarRecord(optionalCarRecord.get());
        this.declarationRepository.save(mappedEntity);
        return true;
    }
}
