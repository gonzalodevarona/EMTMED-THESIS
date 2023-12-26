package com.emt.med.consumable;

import com.emt.med.batch.BatchEntity;
import com.emt.med.inventoryOrder.InventoryOrderEntityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConsumableEntityService {
    ConsumableEntity getConsumableEntityById(Long consumableEntityId);
    ConsumableEntityDTO getConsumableEntityDTOById(Long consumableEntityId);
    List<ConsumableEntityDTO> getAllConsumables();
    List<ConsumableEntityDTO> getAllConsumablesInStock();
    List<ConsumableEntityDTO> getAllConsumablesNoBatches();

    ConsumableEntity saveConsumableEntity(ConsumableEntity consumableEntity);
    ConsumableEntityDTO addConsumable(ConsumableEntityDTO consumableEntityDTO);
    InventoryOrderEntityDTO processNewBatches(ConsumableEntity consumable);

    ConsumableEntityDTO removeWeightUnitFromConsumable(Long consumableEntityId);

    ConsumableEntityDTO removeCountingUnitFromConsumable(Long consumableEntityId);

    public ConsumableEntityDTO recalculateQuantity(Long consumableEntityId);

    // Batches
    ConsumableEntity addBatchesToConsumable(List<BatchEntity> batchEntities, ConsumableEntity consumable);

    ConsumableEntityDTO removeBatchFromConsumable(Long consumableEntityId, Long batchEntityId);


    ConsumableEntityDTO updateConsumable(ConsumableEntityDTO consumableEntityDTO);

    void deleteConsumable(Long id);
}
