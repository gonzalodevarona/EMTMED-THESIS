package com.emt.med.consumable;

import com.emt.med.batch.BatchEntity;
import com.emt.med.countingUnit.CountingUnitEntity;
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

    private SupplyService supplyService;
    static ConsumableEntityMapper consumableEntityMapper = Mappers.getMapper(ConsumableEntityMapper.class);

    public ConsumableEntityServiceImpl(ConsumableEntityRepository consumableEntityRepository, SupplyService supplyService) {
        this.consumableEntityRepository = consumableEntityRepository;
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
    public Supply addBatchToConsumable(BatchEntity batchEntity, ConsumableEntity consumable) {
//        return saveConsumableEntity((ConsumableEntity) supplyService.addBatchToSupply(batchEntity, consumable));
        return null;
    }

//    @Override
//    @Transactional
//    public ConsumableEntityDTO removeBatchFromConsumable(Long consumableEntityId, Long batchEntityId) {
//
//        return consumableEntityMapper.toDTO(saveConsumableEntity((ConsumableEntity) supplyService.removeBatchFromSupply(getConsumableEntityById(consumableEntityId),)));
//    }


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
