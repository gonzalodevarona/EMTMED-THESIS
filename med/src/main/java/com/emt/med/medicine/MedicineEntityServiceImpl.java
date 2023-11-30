package com.emt.med.medicine;

import com.emt.med.location.LocationRepository;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicationBatch.MedicationBatchEntityRepository;
import com.emt.med.supply.SupplyService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineEntityServiceImpl implements MedicineEntityService {
    
    private MedicineEntityRepository medicineEntityRepository;

    private SupplyService supplyService;

    private MedicationBatchEntityRepository medicationBatchEntityRepository;

    private LocationRepository locationRepository;

    static MedicineEntityMapper medicineEntityMapper = Mappers.getMapper(MedicineEntityMapper.class);

    public MedicineEntityServiceImpl(MedicineEntityRepository medicineEntityRepository, SupplyService supplyService, MedicationBatchEntityRepository medicationBatchEntityRepository, LocationRepository locationRepository) {
        this.medicineEntityRepository = medicineEntityRepository;
        this.supplyService = supplyService;
        this.medicationBatchEntityRepository = medicationBatchEntityRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public MedicineEntityDTO getMedicineEntityDTOById(Long medicineEntityId) {
        MedicineEntity medicineEntity = medicineEntityRepository.findById(medicineEntityId).orElseThrow(() -> new RuntimeException("No medicine found with id "+medicineEntityId));
        return medicineEntityMapper.toDTO(medicineEntity);
    }

    @Override
    public MedicineEntity getMedicineEntityById(Long medicineEntityId) {
        return medicineEntityRepository.findById(medicineEntityId).orElseThrow(() -> new RuntimeException("No medicine found with id "+medicineEntityId));
    }

    @Override
    public List<MedicineEntityDTO> getAllMedicines() {
        return medicineEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "activePharmaceuticalIngredient")).stream().map(medicineEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<MedicineEntityDTO> getAllMedicinesNoOrdersNoBatches() {
        return medicineEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "activePharmaceuticalIngredient")).stream().map(medicineEntityMapper::toDTONoOrdersNoBatches).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public MedicineEntityDTO addMedicine(MedicineEntityDTO medicineEntityDTO) {
        if (medicineEntityDTO.getId() != null) {
            throw new RuntimeException("A new medicine cannot already have an ID");
        }
        MedicineEntity medicineEntity = medicineEntityMapper.toEntity((MedicineEntityDTO) medicineEntityDTO);
        medicineEntity = (MedicineEntity) supplyService.addRelationships(medicineEntity);

        addMedicationBatchesToMedicine(medicineEntity.getBatches(), medicineEntity);

        medicineEntity = medicineEntityRepository.save(medicineEntity);


        return medicineEntityMapper.toDTO(medicineEntity);
    }



    @Override
    @Transactional
    public MedicineEntityDTO removeWeightUnitFromMedicine(Long medicineEntityId) {
        return medicineEntityMapper.toDTO(saveMedicineEntity((MedicineEntity) supplyService.removeWeightUnitFromSupply(getMedicineEntityById(medicineEntityId))));
    }


    @Override
    @Transactional
    public MedicineEntityDTO removeCountingUnitFromMedicine(Long medicineEntityId) {
        return medicineEntityMapper.toDTO(saveMedicineEntity((MedicineEntity) supplyService.removeCountingUnitFromSupply(getMedicineEntityById(medicineEntityId))));
    }


    @Override
    @Transactional
    public MedicineEntity addMedicationBatchesToMedicine(List<MedicationBatchEntity> medicationBatchEntities, MedicineEntity medicine){

        if (medicationBatchEntities != null && medicationBatchEntities.size()>0) {

            if (medicine.getBatches() == null) {
                medicine.setBatches(new ArrayList<MedicationBatchEntity>());
            }

            List<MedicationBatchEntity> addedBatches = new ArrayList<MedicationBatchEntity>();

            for (MedicationBatchEntity medicationBatchEntity : medicationBatchEntities) {
                if (medicationBatchEntity.getId() == null) {

                    medicationBatchEntity =  medicationBatchEntityRepository.save(medicationBatchEntity);
                }

                Long medicationBatchId = medicationBatchEntity.getId();

                medicationBatchEntity = medicationBatchEntityRepository.findById(medicationBatchId).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchId));
                medicationBatchEntity.setMedicine(medicine);


                addedBatches.add(medicationBatchEntity);
            }

            medicine.getBatches().clear();
            medicine.getBatches().addAll(addedBatches);
        }

        return medicineEntityRepository.save(medicine);
    }





    @Override
    @Transactional
    public MedicineEntityDTO removeMedicationBatchFromMedicine(Long medicineId, Long medicationBatchId) {
        MedicineEntity medicine = medicineEntityRepository.findById(medicineId).orElseThrow(() -> new RuntimeException("No medicine entity found with id "+medicineId));
        MedicationBatchEntity medicationBatchEntity = medicationBatchEntityRepository.findById(medicationBatchId).orElseThrow(() -> new RuntimeException("No medication batch entity found with id "+medicationBatchId));

        medicine.getBatches().remove(medicationBatchEntity);

        medicationBatchEntity.setMedicine(null);

        return medicineEntityMapper.toDTO(saveMedicineEntity(medicine));
    }


    @Override
    @Transactional
    public MedicineEntity saveMedicineEntity(MedicineEntity medicineEntity) {
        return medicineEntityRepository.save(medicineEntity);
    }


    @Override
    @Transactional
    public MedicineEntityDTO updateMedicine(MedicineEntityDTO medicineEntityDTO) {
        MedicineEntity existingFieldEntity = medicineEntityRepository.findById(medicineEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No medicine found with id "+medicineEntityDTO.getId()));
        medicineEntityMapper.updateMedicineFromDTO((MedicineEntityDTO) medicineEntityDTO, existingFieldEntity);
        return medicineEntityMapper.toDTO(medicineEntityRepository.save(existingFieldEntity));
    }

    @Override
    @Transactional
    public void deleteMedicine(Long id) {
            medicineEntityRepository.deleteById(id);
    }


}
