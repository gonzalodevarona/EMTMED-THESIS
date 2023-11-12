package com.emt.med.consumable;

import com.emt.med.batch.BatchEntity;
import com.emt.med.batch.BatchEntityRepository;
import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.supply.Supply;
import com.emt.med.supply.SupplyService;
import com.emt.med.weightUnit.WeightUnitEntity;
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

    private SupplyService supplyService;
    static ConsumableEntityMapper consumableEntityMapper = Mappers.getMapper(ConsumableEntityMapper.class);

    public ConsumableEntityServiceImpl(ConsumableEntityRepository consumableEntityRepository, BatchEntityRepository batchEntityRepository, SupplyService supplyService) {
        this.consumableEntityRepository = consumableEntityRepository;
        this.batchEntityRepository = batchEntityRepository;
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
    public ConsumableEntity saveConsumableEntity(ConsumableEntity consumableEntity) {
        return consumableEntityRepository.save(consumableEntity);
    }

    @Override
    @Transactional
    public ConsumableEntityDTO addConsumable(ConsumableEntityDTO consumableEntityDTO) {
        if (consumableEntityDTO.getId() != null) {
            throw new RuntimeException("A new consumable cannot already have an ID");
        }
        ConsumableEntity consumableEntity = consumableEntityMapper.toEntity(consumableEntityDTO);
        consumableEntity = consumableEntityRepository.save(consumableEntity);
        addWeightUnitToConsumable(consumableEntity.getWeightUnit(), consumableEntity);
        addCountingUnitToConsumable(consumableEntity.getCountingUnit(), consumableEntity);
        addBatchesToConsumable(consumableEntity.getBatches(), consumableEntity);

        return consumableEntityMapper.toDTO(consumableEntity);
    }


    @Override
    @Transactional
    public Supply addWeightUnitToConsumable(WeightUnitEntity weightUnit, ConsumableEntity consumable) {
        return saveConsumableEntity((ConsumableEntity) supplyService.addWeightUnitToSupply(weightUnit, consumable));
    }

    @Override
    @Transactional
    public ConsumableEntityDTO removeWeightUnitFromConsumable(Long consumableEntityId) {
        return consumableEntityMapper.toDTO(saveConsumableEntity((ConsumableEntity) supplyService.removeWeightUnitFromSupply(getConsumableEntityById(consumableEntityId))));
    }

    @Override
    @Transactional
    public Supply addCountingUnitToConsumable(CountingUnitEntity countingUnit, ConsumableEntity consumable) {
        return saveConsumableEntity((ConsumableEntity) supplyService.addCountingUnitToSupply(countingUnit, consumable));
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

    @Override
    @Transactional
    public ConsumableEntity removeBatchFromMedicine(ConsumableEntity consumable, BatchEntity batchEntity) {
        return null;
    }



    @Override
    @Transactional
    public ConsumableEntityDTO updateConsumable(ConsumableEntityDTO consumableEntityDTO) {
        ConsumableEntity existingFieldEntity = consumableEntityRepository.findById(consumableEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No field found with id "+consumableEntityDTO.getId()));
        consumableEntityMapper.updateConsumableFromDTO(consumableEntityDTO, existingFieldEntity);
        return consumableEntityMapper.toDTO(consumableEntityRepository.save(existingFieldEntity));
    }

    @Override
    @Transactional
    public void deleteConsumable(Long id) {
        consumableEntityRepository.deleteById(id);
    }
}
