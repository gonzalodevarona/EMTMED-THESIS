package com.emt.med.consumable;

import com.emt.med.batch.BatchEntity;
import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.supply.Supply;
import com.emt.med.weightUnit.WeightUnitEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConsumableEntityService {
    ConsumableEntity getConsumableEntityById(Long consumableEntityId);
    ConsumableEntityDTO getConsumableEntityDTOById(Long consumableEntityId);
    List<ConsumableEntityDTO> getAllConsumables();

    List<ConsumableEntityDTO> getAllConsumablesNoOrdersNoBatches();

    ConsumableEntity saveConsumableEntity(ConsumableEntity consumableEntity);
    ConsumableEntityDTO addConsumable(ConsumableEntityDTO consumableEntityDTO);


    Supply addWeightUnitToConsumable(WeightUnitEntity weightUnit, ConsumableEntity consumable);

    ConsumableEntityDTO removeWeightUnitFromConsumable(Long consumableEntityId);

    Supply addCountingUnitToConsumable(CountingUnitEntity countingUnit, ConsumableEntity consumable);

    ConsumableEntityDTO removeCountingUnitFromConsumable(Long consumableEntityId);

    // Batches
    ConsumableEntity addBatchesToConsumable(List<BatchEntity> batchEntities, ConsumableEntity consumable);

    ConsumableEntityDTO removeBatchFromConsumable(Long consumableEntityId, Long batchEntityId);


    ConsumableEntityDTO updateConsumable(ConsumableEntityDTO consumableEntityDTO);

    void deleteConsumable(Long id);
}
