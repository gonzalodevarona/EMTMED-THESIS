package com.emt.med.batchRequest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BatchRequestEntityService {
    BatchRequestEntity getBatchRequestById(Long batchRequestEntityId);

    BatchRequestEntityDTO getBatchRequestDTOtById(Long batchRequestEntityId);
    List<BatchRequestEntityDTO> getAllBatchRequests();
    BatchRequestEntityDTO addBatchRequest(BatchRequestEntityDTO batchRequestEntityDTO);

    BatchRequestEntity saveBatchRequest(BatchRequestEntity batchRequestEntity);


}
