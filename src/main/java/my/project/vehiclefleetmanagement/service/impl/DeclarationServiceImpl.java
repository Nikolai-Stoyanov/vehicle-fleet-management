package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.car.carPerson.CarPersonDTO;
import my.project.vehiclefleetmanagement.model.dtos.declaration.*;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuel.FuelDTO;
import my.project.vehiclefleetmanagement.model.dtos.nomenclatures.fuelSupplier.FuelSupplierDTO;
import my.project.vehiclefleetmanagement.model.entity.car.CarRecord;
import my.project.vehiclefleetmanagement.model.entity.car.RegistrationCertificateData;
import my.project.vehiclefleetmanagement.model.entity.declaration.Declaration;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelEntity;
import my.project.vehiclefleetmanagement.model.entity.nomenclatures.FuelSupplier;
import my.project.vehiclefleetmanagement.repository.*;
import my.project.vehiclefleetmanagement.service.DeclarationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DeclarationServiceImpl implements DeclarationService {
    private final RegistrationCertificateDataRepository registrationCertificateDataRepository;
    private final FuelRepository fuelRepository;
    private final FuelSupplierRepository fuelSupplierRepository;
    private final DeclarationRepository declarationRepository;
    private final ModelMapper modelMapper;
    private final CarRecordRepository carRecordRepository;

    public DeclarationServiceImpl(RegistrationCertificateDataRepository registrationCertificateDataRepository, FuelRepository fuelRepository, FuelSupplierRepository fuelSupplierRepository, DeclarationRepository declarationRepository, ModelMapper modelMapper, CarRecordRepository carRecordRepository) {
        this.registrationCertificateDataRepository = registrationCertificateDataRepository;
        this.fuelRepository = fuelRepository;
        this.fuelSupplierRepository = fuelSupplierRepository;
        this.declarationRepository = declarationRepository;
        this.modelMapper = modelMapper;
        this.carRecordRepository = carRecordRepository;
    }

    @Override
    public boolean createDeclaration(DeclarationCreateDTO declarationCreateDTO) {
        Optional<RegistrationCertificateData> optionalData =
                this.registrationCertificateDataRepository.findByRegistrationNumber(declarationCreateDTO.getRegistrationNumber());

        if (optionalData.isEmpty()) {
            throw new AppException(String.format("Car record with registration number %s is not found!"
                    , declarationCreateDTO.getRegistrationNumber()),
                    HttpStatus.NOT_FOUND);
        }

        CarRecord carRecord =
                this.carRecordRepository.findByRegistrationCertificateData(optionalData.get());
        Optional<Declaration> optionalDeclaration =
                this.declarationRepository.findByPeriodAndCarRecord(
                        declarationCreateDTO.getPeriod(), carRecord);

        if (optionalDeclaration.isPresent()) {
            throw new AppException(String.format("Declaration for registration number %s and period %s is already exists!"
                    , declarationCreateDTO.getRegistrationNumber(), declarationCreateDTO.getPeriod()),
                    HttpStatus.NOT_FOUND);
        }

        Declaration mappedEntity = modelMapper.map(declarationCreateDTO, Declaration.class);
        mappedEntity.setCarRecord(carRecord);
        mappedEntity.setDate(LocalDate.parse(declarationCreateDTO.getDate()));
        mappedEntity.setCreatedBy("currentPrincipalName");
        mappedEntity.setCreatedAt(LocalDate.now());


        this.declarationRepository.save(mappedEntity);
        throw new AppException("Declaration successfully created!", HttpStatus.OK);
    }

    @Override
    public List<DeclarationListDTO> getAllDeclarations() {
        List<Declaration> declarationList = this.declarationRepository.findAll();

        List<DeclarationListDTO> declarationListDTOS = new ArrayList<>();
        for (Declaration declaration : declarationList) {
            DeclarationListDTO model = modelMapper.map(declaration, DeclarationListDTO.class);

            CarRecord carRecord = this.carRecordRepository.findById(declaration.getCarRecord().getId()).get();
            model.setRegistrationNumber(carRecord.getRegistrationCertificateData().getRegistrationNumber());
            model.setResponsible(carRecord.getResponsible().getFullName());
            model.setDriver(carRecord.getDriver().getFullName());
            declarationListDTOS.add(model);
        }

        return declarationListDTOS;
    }

    @Override
    public DeclarationDTO getDeclarationById(Long id) {
        Optional<Declaration> declaration = this.declarationRepository.findById(id);
        if (declaration.isEmpty()) {
            throw new AppException("Declaration is not found!", HttpStatus.NOT_FOUND);
        }
        DeclarationDTO declarationDTO = modelMapper.map(declaration, DeclarationDTO.class);

        CarRecord carRecord = declaration.get().getCarRecord();
        declarationDTO.setRegistrationNumber(carRecord.getRegistrationCertificateData().getRegistrationNumber());
        declarationDTO.setResponsible(carRecord.getResponsible().getFullName());
        declarationDTO.setFuelType(String.valueOf(carRecord.getFuelType()));
        Optional<FuelEntity> fuel=this.fuelRepository.findById(declaration.get().getFuelKind());
        Optional<FuelSupplier> fuelSupplier=this.fuelSupplierRepository.findById(declaration.get().getFuelSupplier());
        declarationDTO.setFuelKind(modelMapper.map(fuel, FuelDTO.class));
        declarationDTO.setFuelSupplier(modelMapper.map(fuelSupplier, FuelSupplierDTO.class));
        return declarationDTO;
    }

    @Override
    public void deleteDeclaration(Long id) {
        Optional<Declaration> declaration = this.declarationRepository.findById(id);
        if (declaration.isEmpty()) {
            throw new AppException("Declaration is not found!", HttpStatus.NOT_FOUND);
        }
        this.declarationRepository.deleteById(id);
        throw new AppException("Declaration successfully deleted!", HttpStatus.OK);
    }

    @Override
    public boolean updateDeclaration(Long id, DeclarationEditDTO declarationEditDTO) {
        Optional<Declaration> optionalDeclaration = this.declarationRepository.findById(id);

        if (optionalDeclaration.isEmpty()) {
            throw new AppException("Declaration is not found!", HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(declarationEditDTO.getPeriod(), optionalDeclaration.get().getPeriod())) {
            Optional<Declaration> optionalDeclarationWithSamePeriodAndCarRecord
                    = this.declarationRepository
                    .findByPeriodAndCarRecord(declarationEditDTO.getPeriod(),optionalDeclaration.get().getCarRecord());

            if (optionalDeclarationWithSamePeriodAndCarRecord.isPresent()) {
                throw new AppException(String.format("Declaration for registration number %s and period %s is already exists!"
                        , declarationEditDTO.getRegistrationNumber(), declarationEditDTO.getPeriod()),
                        HttpStatus.NOT_FOUND);
            }
        }


        Declaration editedDeclaration = optionalDeclaration.get();
        modelMapper.map(declarationEditDTO, editedDeclaration);
        editedDeclaration.setUpdatedBy("currentPrincipalName");
        editedDeclaration.setUpdatedAt(LocalDate.now());

        this.declarationRepository.save(editedDeclaration);
        throw new AppException("Declaration successfully updated!", HttpStatus.OK);
    }
}
