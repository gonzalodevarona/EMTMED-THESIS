package com.emt.med.consumable;

import com.emt.med.batch.BatchEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConsumableEntityService {
    ConsumableEntity getConsumableEntityById(Long consumableEntityId);
    ConsumableEntityDTO getConsumableEntityDTOById(Long consumableEntityId);
    List<ConsumableEntityDTO> getAllConsumables();

    List<ConsumableEntityDTO> getAllConsumablesNoBatches();

    ConsumableEntity saveConsumableEntity(ConsumableEntity consumableEntity);
    ConsumableEntityDTO addConsumable(ConsumableEntityDTO consumableEntityDTO);

    ConsumableEntityDTO removeWeightUnitFromConsumable(Long consumableEntityId);

    ConsumableEntityDTO removeCountingUnitFromConsumable(Long consumableEntityId);

    // Batches
    ConsumableEntity addBatchesToConsumable(List<BatchEntity> batchEntities, ConsumableEntity consumable);

    ConsumableEntityDTO removeBatchFromConsumable(Long consumableEntityId, Long batchEntityId);


    ConsumableEntityDTO updateConsumable(ConsumableEntityDTO consumableEntityDTO);

    void deleteConsumable(Long id);
}
