package com.emt.med.batch;

import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.consumable.ConsumableEntityMapper;
import com.emt.med.consumable.ConsumableEntityRepository;
import com.emt.med.location.LocationDTO;
import com.emt.med.location.LocationMapper;
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

    private ConsumableEntityRepository consumableEntityRepository;

    static BatchEntityMapper batchEntityMapper = Mappers.getMapper(BatchEntityMapper.class);

    static ConsumableEntityMapper consumableEntityMapper = Mappers.getMapper(ConsumableEntityMapper.class);

    static LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);


    public BatchEntityServiceImpl(BatchEntityRepository batchEntityRepository, ConsumableEntityRepository consumableEntityRepository) {
        this.batchEntityRepository = batchEntityRepository;
        this.consumableEntityRepository = consumableEntityRepository;
    }

    @Override
    public BatchEntityDTO getBatchEntityDTOById(Long batchEntityId) {
        BatchEntity batchEntity = batchEntityRepository.findById(batchEntityId).orElseThrow(() -> new RuntimeException("No batch found with id "+batchEntityId));
        return batchEntityMapper.toDTO(batchEntity);
    }

    @Override
    public ConsumableEntityDTO getConsumableByBatchId(Long batchEntityId){
        BatchEntity batchEntity = batchEntityRepository.findById(batchEntityId).orElseThrow(() -> new RuntimeException("No batch found with id "+batchEntityId));
        return consumableEntityMapper.toDTO(batchEntity.getConsumable());
    }

    @Override
    public LocationDTO getLocationByBatchId(Long batchEntityId){
        BatchEntity batchEntity = batchEntityRepository.findById(batchEntityId).orElseThrow(() -> new RuntimeException("No batch found with id "+batchEntityId));
        return locationMapper.toDTO(batchEntity.getLocation());
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
    @Transactional
    public BatchEntity decrementBatchQuantity(Long batchEntityId, Integer quantityToDecrement) {
        BatchEntity batchEntity = batchEntityRepository.findById(batchEntityId).orElseThrow(() -> new RuntimeException("No batch found with id "+batchEntityId));
        Integer difference = batchEntity.getQuantity() - quantityToDecrement;

        if (difference >= 0) {
            batchEntity.setQuantity(difference);
            batchEntity.getConsumable().setQuantity(batchEntity.getConsumable().getQuantity() - quantityToDecrement);
            consumableEntityRepository.save(batchEntity.getConsumable());
            batchEntity = batchEntityRepository.save(batchEntity);
        } else {
            throw new RuntimeException("Error: insufficient quantity to satisfy this request");
        }

        return batchEntity;
    }

    @Override
    public BatchEntity saveBatch(BatchEntity batchEntity) {
        return batchEntityRepository.save(batchEntity);
    }

    @Override
    @Transactional
    public BatchEntityDTO updateBatch(BatchEntity batchEntity) {

        BatchEntity existingBatchEntity = batchEntityRepository.findById(batchEntity.getId()).orElseThrow(() -> new RuntimeException("No batch found with id "+batchEntity.getId()));

        if(batchEntity.getQuantity() != null) {
            existingBatchEntity.setQuantity(batchEntity.getQuantity());
        }

        if(batchEntity.getConsumable() != null) {
            existingBatchEntity.setConsumable(batchEntity.getConsumable());
        }
//        if(batchEntity.getLocation() != null) {
//            existingBatchEntity.setLocation(batchEntity.getLocation());
//        }
        if(batchEntity.getManufacturer() != null) {
            existingBatchEntity.setManufacturer(batchEntity.getManufacturer());
        }
        if(batchEntity.getExpirationDate() != null) {
            existingBatchEntity.setExpirationDate(batchEntity.getExpirationDate());
        }
//        if(batchEntity.getAdministrationRoute() != null) {
//            existingBatchEntity.setAdministrationRoute(batchEntity.getAdministrationRoute());
//        }
        if(batchEntity.getStatus() != null) {
            existingBatchEntity.setStatus(batchEntity.getStatus());
        }

        return batchEntityMapper.toDTO(batchEntityRepository.save(existingBatchEntity));
    }

    @Override
    @Transactional
    public void deleteBatch(Long id) {
        batchEntityRepository.deleteById(id);
    }

}
