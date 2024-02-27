package com.emt.med.batch;

import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.location.LocationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BatchEntityService {

    BatchEntityDTO getBatchEntityDTOById(Long batchEntityId);
    List<BatchEntityDTO> getAllBatches();

    ConsumableEntityDTO getConsumableByBatchId(Long batchEntityId);

    LocationDTO getLocationByBatchId(Long batchEntityId);
    BatchEntityDTO addBatch(BatchEntityDTO batchDTO);

    BatchEntity decrementBatchQuantity(Long batchEntityId, Integer quantityToDecrement);
    BatchEntity saveBatch(BatchEntity batchEntity);

    BatchEntityDTO updateBatch(BatchEntity batchEntity);

    void deleteBatch(Long id);


}
