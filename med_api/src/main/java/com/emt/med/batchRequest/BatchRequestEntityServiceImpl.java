package com.emt.med.batchRequest;

import com.emt.med.batch.BatchEntityRepository;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchRequestEntityServiceImpl implements BatchRequestEntityService {

    private BatchRequestEntityRepository batchRequestEntityRepository;
    private BatchEntityRepository batchEntityRepository;

    static BatchRequestEntityMapper batchRequestEntityMapper = Mappers.getMapper(BatchRequestEntityMapper.class);

    public BatchRequestEntityServiceImpl(BatchRequestEntityRepository batchRequestEntityRepository, BatchEntityRepository batchEntityRepository) {
        this.batchRequestEntityRepository = batchRequestEntityRepository;
        this.batchEntityRepository = batchEntityRepository;
    }

    @Override
    public BatchRequestEntity getBatchRequestById(Long batchRequestEntityId) {
        return batchRequestEntityRepository.findById(batchRequestEntityId).orElseThrow(() -> new RuntimeException("No batch request found with id "+batchRequestEntityId));
    }

    @Override
    public BatchRequestEntityDTO getBatchRequestDTOtById(Long batchRequestEntityId) {
        BatchRequestEntity batchRequestEntity = batchRequestEntityRepository.findById(batchRequestEntityId).orElseThrow(() -> new RuntimeException("No batch request found with id "+batchRequestEntityId));
        return batchRequestEntityMapper.toDTO(batchRequestEntity);
    }

    @Override
    public List<BatchRequestEntityDTO> getAllBatchRequests() {
        return batchRequestEntityRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().map(batchRequestEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    @Transactional
    public BatchRequestEntityDTO addBatchRequest(BatchRequestEntityDTO batchRequestEntityDTO) {
        if (batchRequestEntityDTO.getId() != null) {
            throw new RuntimeException("A medication batch request unit cannot already have an ID");
        }
        BatchRequestEntity batchRequestEntity = batchRequestEntityMapper.toEntity(batchRequestEntityDTO);

        return batchRequestEntityMapper.toDTO(saveBatchRequest(batchRequestEntity));
    }

    @Override
    @Transactional
    public BatchRequestEntity saveBatchRequest(BatchRequestEntity batchRequestEntity) {
        return batchRequestEntityRepository.save(batchRequestEntity);
    }
}
