package com.emt.med.medicine;


import com.emt.med.batch.BatchEntity;
import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicationBatch.MedicationBatchEntityRepository;
import com.emt.med.medicationBatch.MedicationBatchEntityService;
import com.emt.med.supply.Supply;
import com.emt.med.supply.SupplyService;
import com.emt.med.weightUnit.WeightUnitEntity;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineEntityServiceImpl implements MedicineEntityService {
    
    private MedicineEntityRepository medicineEntityRepository;

    private SupplyService supplyService;

    private MedicationBatchEntityService medicationBatchEntityService;
    private MedicationBatchEntityRepository medicationBatchEntityRepository;

    static MedicineEntityMapper medicineEntityMapper = Mappers.getMapper(MedicineEntityMapper.class);

    public MedicineEntityServiceImpl(MedicineEntityRepository medicineEntityRepository, SupplyService supplyService, MedicationBatchEntityService medicationBatchEntityService, MedicationBatchEntityRepository medicationBatchEntityRepository) {
        this.medicineEntityRepository = medicineEntityRepository;
        this.supplyService = supplyService;
        this.medicationBatchEntityService = medicationBatchEntityService;
        this.medicationBatchEntityRepository = medicationBatchEntityRepository;
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
    @Transactional
    public MedicineEntityDTO addMedicine(MedicineEntityDTO medicineEntityDTO) {
        if (medicineEntityDTO.getId() != null) {
            throw new RuntimeException("A new medicine cannot already have an ID");
        }
        MedicineEntity medicineEntity = medicineEntityMapper.toEntity((MedicineEntityDTO) medicineEntityDTO);
        medicineEntity = medicineEntityRepository.save(medicineEntity);
        addWeightUnitToMedicine(medicineEntity.getWeightUnit(), medicineEntity);
        addCountingUnitToMedicine(medicineEntity.getCountingUnit(), medicineEntity);
        addMedicationBatchesToMedicine(medicineEntity.getBatches(), medicineEntity);

        return medicineEntityMapper.toDTO(medicineEntity);
    }

    @Override
    @Transactional
    public Supply addWeightUnitToMedicine(WeightUnitEntity weightUnit, MedicineEntity medicine) {
        return saveMedicineEntity((MedicineEntity) supplyService.addWeightUnitToSupply(weightUnit, medicine));
    }

    @Override
    @Transactional
    public MedicineEntityDTO removeWeightUnitFromMedicine(Long medicineEntityId) {
        return medicineEntityMapper.toDTO(saveMedicineEntity((MedicineEntity) supplyService.removeWeightUnitFromSupply(getMedicineEntityById(medicineEntityId))));
    }

    @Override
    @Transactional
    public Supply addCountingUnitToMedicine(CountingUnitEntity countingUnit, MedicineEntity medicine) {
        return saveMedicineEntity((MedicineEntity) supplyService.addCountingUnitToSupply(countingUnit, medicine));
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
                medicationBatchEntity.setMedicine(medicine);
                if (medicationBatchEntity.getId() == null) {

                    medicationBatchEntity =  medicationBatchEntityRepository.save(medicationBatchEntity);
                }

                addedBatches.add(medicationBatchEntity);
            }

            medicine.getBatches().clear();
            medicine.getBatches().addAll(addedBatches);
        }

        return medicineEntityRepository.save(medicine);
    }





    @Override
    public MedicineEntity removeMedicationBatchFromMedicine(MedicineEntity medicine, MedicationBatchEntity medicationBatchEntity) {
        return null;
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
