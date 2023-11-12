package com.emt.med.batch;


import com.emt.med.medicationBatch.MedicationBatchEntity;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchEntityServiceImpl implements BatchEntityService {

    private BatchEntityRepository batchEntityRepository;

    static BatchEntityMapper batchEntityMapper = Mappers.getMapper(BatchEntityMapper.class);

    public BatchEntityServiceImpl(BatchEntityRepository batchEntityRepository) {
        this.batchEntityRepository = batchEntityRepository;
    }

    @Override
    public BatchEntity getBatchEntityById(Long batchEntityId) {
        return batchEntityRepository.findById(batchEntityId).orElseThrow(() -> new RuntimeException("No batch found with id "+batchEntityId));
    }

    @Override
    public BatchEntityDTO getBatchEntityDTOById(Long batchEntityId) {
        BatchEntity batchEntity = batchEntityRepository.findById(batchEntityId).orElseThrow(() -> new RuntimeException("No batch found with id "+batchEntityId));
        return batchEntityMapper.toDTO(batchEntity);
    }

    @Override
    public List<BatchEntityDTO> getAllBatches() {
        return batchEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "expirationDate")).stream().map(batchEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public BatchEntityDTO addBatch(BatchEntityDTO batchDTO) {
        if (batchDTO.getId() != null) {
            throw new RuntimeException("A new batch cannot already have an ID");
        }
        BatchEntity batchEntity = batchEntityMapper.toEntity(batchDTO);
        batchEntity = batchEntityRepository.save(batchEntity);
        return batchEntityMapper.toDTO(batchEntity);
    }

    @Override
    public BatchEntity saveBatch(BatchEntity batchEntity) {
        return batchEntityRepository.save(batchEntity);
    }

    @Override
    @Transactional
    public BatchEntityDTO updateBatch(BatchEntityDTO batchDTO) {
        BatchEntity existingBatchEntity = batchEntityRepository.findById(batchDTO.getId()).orElseThrow(() -> new RuntimeException("No batch found with id "+batchDTO.getId()));
        batchEntityMapper.updateBatchFromDto(batchDTO, existingBatchEntity);
        return batchEntityMapper.toDTO(batchEntityRepository.save(existingBatchEntity));
    }

    @Override
    @Transactional
    public void deleteBatch(Long id) {
        batchEntityRepository.deleteById(id);
    }

}
