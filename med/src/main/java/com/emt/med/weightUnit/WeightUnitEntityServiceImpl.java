package com.emt.med.weightUnit;

import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeightUnitEntityServiceImpl implements WeightUnitEntityService {

    private WeightUnitEntityRepository weightUnitEntityRepository;

    static WeightUnitEntityMapper weightUnitEntityMapper = Mappers.getMapper(WeightUnitEntityMapper.class);

    public WeightUnitEntityServiceImpl(WeightUnitEntityRepository weightUnitEntityRepository) {
        this.weightUnitEntityRepository = weightUnitEntityRepository;
    }


    @Override
    public WeightUnitEntityDTO getWeightUnitById(Long weightUnitEntityId) {
        WeightUnitEntity weightUnitEntity = weightUnitEntityRepository.findById(weightUnitEntityId).orElseThrow(() -> new RuntimeException("No weight unit found with id "+weightUnitEntityId));
        return weightUnitEntityMapper.toDTO(weightUnitEntity);
    }

    @Override
    public List<WeightUnitEntityDTO> getAllWeightUnits() {
        return weightUnitEntityRepository.findAll().stream().map(weightUnitEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public WeightUnitEntityDTO addWeightUnit(WeightUnitEntityDTO weightUnitEntityDTO) {
        if (weightUnitEntityDTO.getId() != null) {
            throw new RuntimeException("A new weight unit cannot already have an ID");
        }
        WeightUnitEntity weightUnitEntity = weightUnitEntityMapper.toEntity(weightUnitEntityDTO);
        weightUnitEntity = weightUnitEntityRepository.save(weightUnitEntity);
        return weightUnitEntityMapper.toDTO(weightUnitEntity);
    }

    @Override
    @Transactional
    public WeightUnitEntityDTO updateWeightUnit(WeightUnitEntityDTO weightUnitEntityDTO) {
        WeightUnitEntity existingWeightUnitEntity = weightUnitEntityRepository.findById(weightUnitEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No weight unit found with id "+ weightUnitEntityDTO.getId()));
        weightUnitEntityMapper.updateWeightUnitFromDTO(weightUnitEntityDTO, existingWeightUnitEntity);
        return weightUnitEntityMapper.toDTO(weightUnitEntityRepository.save(existingWeightUnitEntity));
    }

    @Override
    @Transactional
    public void deleteWeightUnit(Long id) {
        weightUnitEntityRepository.deleteById(id);
    }
}
