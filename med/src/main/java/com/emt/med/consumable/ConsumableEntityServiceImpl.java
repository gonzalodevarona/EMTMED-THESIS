package com.emt.med.consumable;

import com.emt.med.batch.BatchEntity;
import com.emt.med.batch.BatchEntityMapper;
import com.emt.med.batch.BatchEntityRepository;
import com.emt.med.batch.BatchEntityService;
import com.emt.med.supply.SupplyService;
import com.emt.med.weightUnit.WeightUnitEntity;
import com.emt.med.weightUnit.WeightUnitEntityRepository;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumableEntityServiceImpl implements ConsumableEntityService{

    private ConsumableEntityRepository consumableEntityRepository;
    private BatchEntityRepository batchEntityRepository;

    private WeightUnitEntityRepository weightUnitEntityRepository;

    private BatchEntityService batchEntityService;

    private SupplyService supplyService;
    static ConsumableEntityMapper consumableEntityMapper = Mappers.getMapper(ConsumableEntityMapper.class);

    static BatchEntityMapper batchEntityMapper = Mappers.getMapper(BatchEntityMapper.class);

    public ConsumableEntityServiceImpl(ConsumableEntityRepository consumableEntityRepository, BatchEntityRepository batchEntityRepository, WeightUnitEntityRepository weightUnitEntityRepository, BatchEntityService batchEntityService, SupplyService supplyService) {
        this.consumableEntityRepository = consumableEntityRepository;
        this.batchEntityRepository = batchEntityRepository;
        this.weightUnitEntityRepository = weightUnitEntityRepository;
        this.batchEntityService = batchEntityService;
        this.supplyService = supplyService;
    }

    @Override
    public ConsumableEntity getConsumableEntityById(Long consumableEntityId) {
        return consumableEntityRepository.findById(consumableEntityId).orElseThrow(() -> new RuntimeException("No consumable found with id "+consumableEntityId));
    }

    @Override
    public ConsumableEntityDTO getConsumableEntityDTOById(Long consumableEntityId) {
        ConsumableEntity consumableEntity = consumableEntityRepository.findById(consumableEntityId).orElseThrow(() -> new RuntimeException("No consumable found with id "+consumableEntityId));
        return consumableEntityMapper.toDTO(consumableEntity);
    }

    @Override
    public List<ConsumableEntityDTO> getAllConsumables() {
        return consumableEntityRepository.findAll(Sort.by(Sort.Direction.DESC, "quantity")).stream().map(consumableEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public List<ConsumableEntityDTO> getAllConsumablesNoBatches() {
        return consumableEntityRepository.findAll( Sort.by(Sort.Direction.ASC, "name")).stream().map(consumableEntityMapper::toDTONoBatches).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ConsumableEntity saveConsumableEntity(ConsumableEntity consumableEntity) {
        return consumableEntityRepository.save(consumableEntity);
    }

    @Override
    @Transactional
    public ConsumableEntityDTO addConsumable(ConsumableEntityDTO consumableEntityDTO) {
        if (consumableEntityDTO.getId() != null) {
            throw new RuntimeException("A new consumable cannot already have an ID");
        }
        ConsumableEntity consumableEntity = consumableEntityMapper.toEntity((ConsumableEntityDTO) consumableEntityDTO);
        consumableEntity = (ConsumableEntity) supplyService.addRelationships(consumableEntity);

        addBatchesToConsumable(consumableEntity.getBatches(), consumableEntity);

        consumableEntity = consumableEntityRepository.save(consumableEntity);


        return consumableEntityMapper.toDTO(consumableEntity);
    }

    @Override
    @Transactional
    public ConsumableEntityDTO removeWeightUnitFromConsumable(Long consumableEntityId) {
        return consumableEntityMapper.toDTO(saveConsumableEntity((ConsumableEntity) supplyService.removeWeightUnitFromSupply(getConsumableEntityById(consumableEntityId))));
    }

    @Override
    @Transactional
    public ConsumableEntityDTO removeCountingUnitFromConsumable(Long consumableEntityId) {
        return consumableEntityMapper.toDTO(saveConsumableEntity((ConsumableEntity) supplyService.removeCountingUnitFromSupply(getConsumableEntityById(consumableEntityId))));
    }

    @Override
    @Transactional
    public ConsumableEntity addBatchesToConsumable(List<BatchEntity> batchEntities, ConsumableEntity consumable) {
        if (batchEntities != null && batchEntities.size()>0) {

            if (consumable.getBatches() == null) {
                consumable.setBatches(new ArrayList<BatchEntity>());
            }

            List<BatchEntity> addedBatches = new ArrayList<BatchEntity>();

            for (BatchEntity batchEntity : batchEntities) {
                batchEntity.setConsumable(consumable);
                if (batchEntity.getId() == null) {

                    batchEntity =  batchEntityRepository.save(batchEntity);
                }

                addedBatches.add(batchEntity);
            }

            consumable.getBatches().clear();
            consumable.getBatches().addAll(addedBatches);
        }

        return consumableEntityRepository.save(consumable);
    }

    public ConsumableEntity addMedicationBatchesToMedicine(List<BatchEntity> batchEntities, ConsumableEntity consumable) {

        if (batchEntities != null && batchEntities.size()>0) {

            if (consumable.getBatches() == null) {
                consumable.setBatches(new ArrayList<BatchEntity>());
            }

            List<BatchEntity> addedBatches = new ArrayList<BatchEntity>();

            for (BatchEntity batchEntity : batchEntities) {
                if (batchEntity.getId() == null) {

                    batchEntity =  batchEntityRepository.save(batchEntity);
                }

                Long batchId = batchEntity.getId();

                BatchEntity foundBatchEntity = batchEntityRepository.findById(batchId).orElseThrow(() -> new RuntimeException("No batch found with id "+batchId));

                if(foundBatchEntity.getConsumable() == null){
                    foundBatchEntity.setConsumable(consumable);

                    addedBatches.add(foundBatchEntity);
                } else{
                    batchEntityService.updateBatch(batchEntity);
                    continue;
                }

            }

            consumable.getBatches().clear();
            consumable.getBatches().addAll(addedBatches);
        }

        return consumableEntityRepository.save(consumable);
    }

    @Override
    @Transactional
    public ConsumableEntityDTO removeBatchFromConsumable(Long consumableEntityId, Long batchEntityId) {
        ConsumableEntity consumable = consumableEntityRepository.findById(consumableEntityId).orElseThrow(() -> new RuntimeException("No consumable entity found with id "+consumableEntityId));
        BatchEntity batchEntity = batchEntityRepository.findById(batchEntityId).orElseThrow(() -> new RuntimeException("No batch entity found with id "+batchEntityId));

        consumable.getBatches().remove(batchEntity);

        batchEntity.setConsumable(null);

        return consumableEntityMapper.toDTO(saveConsumableEntity(consumable));
    }

    public ConsumableEntityDTO updateConsumable(ConsumableEntityDTO consumableEntityDTO) {
        ConsumableEntity existingConsumable = consumableEntityRepository.findById(consumableEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No consumable found with id "+consumableEntityDTO.getId()));


//        if (consumableEntityDTO.getName() != null) {
//            existingConsumable.setName(consumableEntityDTO.getName());
//        }
        if (consumableEntityDTO.getWeight() != null) {
            existingConsumable.setWeight(consumableEntityDTO.getWeight());
        }
        if (consumableEntityDTO.getQuantity() != null) {
            existingConsumable.setQuantity(consumableEntityDTO.getQuantity());
        }
        if (consumableEntityDTO.getWeightUnit() != null) {
            WeightUnitEntity foundWeightUnit = weightUnitEntityRepository.findById(consumableEntityDTO.getWeightUnit().getId()).orElseThrow(() -> new RuntimeException("No weight unit found with id "+consumableEntityDTO.getWeightUnit().getId()));
            existingConsumable.setWeightUnit(foundWeightUnit);
        }
//        if (consumableEntityDTO.getCountingUnit() != null) {
//            CountingUnitEntity foundCountingUnit = countingUnitEntityRepository.findById(consumableEntityDTO.getWeightUnit().getId()).orElseThrow(() -> new RuntimeException("No counting unit found with id "+consumableEntityDTO.getCountingUnit().getId()));
//            existingConsumable.setCountingUnit(foundCountingUnit);
//        }
//        if (consumableEntityDTO.getActivePharmaceuticalIngredient() != null) {
//            existingConsumable.setActivePharmaceuticalIngredient(consumableEntityDTO.getActivePharmaceuticalIngredient());
//        }
//        if (consumableEntityDTO.getConcentration() != null) {
//            existingConsumable.setConcentration(consumableEntityDTO.getConcentration());
//        }
        if (consumableEntityDTO.getBatches() != null) {


            List<BatchEntity> batches = batchEntityMapper.map(consumableEntityDTO.getBatches());
            for (BatchEntity batch : batches) {
                if(batch.getId() != null){
                    BatchEntity foundBatch = batchEntityRepository.findById(batch.getId()).orElseThrow(() -> new RuntimeException("No batch entity found with id "+batch.getId()));
                    batch.setConsumable(foundBatch.getConsumable());
                }
            }
            addBatchesToConsumable( batches, existingConsumable);
        }

        return consumableEntityMapper.toDTO(consumableEntityRepository.save(existingConsumable));
    }

    @Override
    @Transactional
    public void deleteConsumable(Long id) {
        consumableEntityRepository.deleteById(id);
    }
}
