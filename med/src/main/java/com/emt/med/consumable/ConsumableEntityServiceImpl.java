package com.emt.med.consumable;

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
    static ConsumableEntityMapper consumableEntityMapper = Mappers.getMapper(ConsumableEntityMapper.class);

    public ConsumableEntityServiceImpl(ConsumableEntityRepository consumableEntityRepository) {
        this.consumableEntityRepository = consumableEntityRepository;
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
    @Transactional
    public ConsumableEntityDTO addConsumable(ConsumableEntityDTO consumableEntityDTO) {
        if (consumableEntityDTO.getId() != null) {
            throw new RuntimeException("A new field cannot already have an ID");
        }
        ConsumableEntity consumableEntity = consumableEntityMapper.toEntity(consumableEntityDTO);
        consumableEntity = consumableEntityRepository.save(consumableEntity);
        return consumableEntityMapper.toDTO(consumableEntity);
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
