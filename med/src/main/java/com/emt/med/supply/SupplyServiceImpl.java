package com.emt.med.supply;


import com.emt.med.batch.BatchEntityService;

import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.countingUnit.CountingUnitEntityService;

import com.emt.med.medicationBatch.MedicationBatchEntityService;

import com.emt.med.weightUnit.WeightUnitEntity;
import com.emt.med.weightUnit.WeightUnitEntityService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class SupplyServiceImpl implements SupplyService{

    static SupplyMapper supplyMapper = Mappers.getMapper(SupplyMapper.class);

    // *** Weight Unit ***
    private WeightUnitEntityService weightUnitEntityService;

    // *** Counting Unit ***
    private CountingUnitEntityService countingUnitEntityService;

    // *** Batch ***
    private BatchEntityService batchEntityService;

    // *** Medication Batch ***
    private MedicationBatchEntityService medicationBatchEntityService;

    public SupplyServiceImpl(WeightUnitEntityService weightUnitEntityService, CountingUnitEntityService countingUnitEntityService, BatchEntityService batchEntityService, MedicationBatchEntityService medicationBatchEntityService) {
        this.weightUnitEntityService = weightUnitEntityService;
        this.countingUnitEntityService = countingUnitEntityService;
        this.batchEntityService = batchEntityService;
        this.medicationBatchEntityService = medicationBatchEntityService;
    }


    @Override
    @Transactional
    public Supply addWeightUnitToSupply(WeightUnitEntity weightUnit, Supply supply) {
        if (weightUnit == null){
            throw new RuntimeException("Error: In order to add a medicine, a weight unit must be added or assigned to it");
        } else if (weightUnit.getSupplyList() == null){
            weightUnit.setSupplyList(new ArrayList<Supply>());
        } else if (weightUnit.getId() == null){
            weightUnit = weightUnitEntityService.saveWeightUnitEntity(weightUnit);
        }

        supply.setWeightUnit(weightUnit);
        weightUnit.getSupplyList().add(supply);
        weightUnitEntityService.saveWeightUnitEntity(weightUnit);

        return supply;
    }

    @Override
    @Transactional
    public Supply removeWeightUnitFromSupply(Supply supply) {

        WeightUnitEntity weightUnitEntity = supply.getWeightUnit();

        weightUnitEntity.getSupplyList().remove(supply);

        supply.setWeightUnit(null);
        weightUnitEntityService.saveWeightUnitEntity(weightUnitEntity);
        return supply;
    }

    @Override
    @Transactional
    public Supply addCountingUnitToSupply(CountingUnitEntity countingUnit, Supply supply) {
        if (countingUnit == null){
            throw new RuntimeException("Error: In order to add a medicine, a counting unit must be added or assigned to it");
        } else if (countingUnit.getSupplyList() == null){
            countingUnit.setSupplyList(new ArrayList<Supply>());
        } else if (countingUnit.getId() == null){
            countingUnit = countingUnitEntityService.saveCountingUnit(countingUnit);
        }

        supply.setCountingUnit(countingUnit);
        countingUnit.getSupplyList().add(supply);

        countingUnitEntityService.saveCountingUnit(countingUnit);

        return supply;
    }

    @Override
    @Transactional
    public Supply removeCountingUnitFromSupply(Supply supply) {
        CountingUnitEntity countingUnitEntity = supply.getCountingUnit();

        countingUnitEntity.getSupplyList().remove(supply);

        supply.setCountingUnit(null);
        countingUnitEntityService.saveCountingUnit(countingUnitEntity);
        return supply;
    }

//    @Override
//    @Transactional
//    public Supply addBatchesToSupply(List<BatchEntity> batchEntities, Supply supply) {
//        if (batchEntities != null && batchEntities.size()>0) {
//
//            if (supply.getBatches() == null) {
//                supply.setBatches(new ArrayList<BatchEntity>());
//            }
//
//            for (BatchEntity batchEntity : batchEntities) {
//                boolean instanceofMedicationBatch = batchEntityService.isMedicationBatch(batchEntity);
//
//                if (batchEntity.getId() == null) {
//                    batchEntity = (instanceofMedicationBatch) ? batchEntityService.saveBatch(batchEntity) : medicationBatchEntityService.saveMedicationBatch((MedicationBatchEntity) batchEntity);
//                }
//
//
//                if (isMedicine(supply) && instanceofMedicationBatch )  {
//
//                    supply.getBatches().add((MedicationBatchEntity)batchEntity);
//
//                } else if ((!isMedicine(supply)) && (!instanceofMedicationBatch)){
//                    supply.getBatches().add(batchEntity);
//                }else {
//                    throw new RuntimeException("Error: a batch must be assigned to a consumable and a medication batch with a medicine only ");
//                }
//
//                batchEntity.setSupply(supply);
//            }
//        }
//        return supply;
//    }

//    @Override
//    @Transactional
//    public Supply removeBatchFromSupply(Supply supply, BatchEntity batchEntity) {
//
//        supply.getBatches().remove(batchEntity);
//        batchEntity.setSupply(null);
//
//        if (batchEntityService.isMedicationBatch(batchEntity)){
//            medicationBatchEntityService.saveMedicationBatch((MedicationBatchEntity) batchEntity);
//        } else{
//            batchEntityService.saveBatch(batchEntity);
//        }
//
//        return supply;
//    }
}
