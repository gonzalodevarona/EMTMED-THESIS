package com.emt.med.batch;

import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BatchEntityService {

    BatchEntityDTO getBatchEntityById(Long batchEntityId);
    List<BatchEntityDTO> getAllBatches();
    BatchEntityDTO addBatch(BatchEntityDTO batchDTO);

    BatchEntityDTO updateBatch(BatchEntityDTO batchDTO);

    void deleteBatch(Long id);


}
