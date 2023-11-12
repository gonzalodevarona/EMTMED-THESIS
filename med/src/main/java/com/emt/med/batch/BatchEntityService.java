package com.emt.med.batch;

import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.weightUnit.WeightUnitEntity;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BatchEntityService {

    BatchEntity getBatchEntityById(Long batchEntityId);

    BatchEntityDTO getBatchEntityDTOById(Long batchEntityId);
    List<BatchEntityDTO> getAllBatches();
    BatchEntityDTO addBatch(BatchEntityDTO batchDTO);

    BatchEntity saveBatch(BatchEntity batchEntity);

    BatchEntityDTO updateBatch(BatchEntityDTO batchDTO);

    void deleteBatch(Long id);


}
