package com.emt.med.value;

import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValueEntityServiceImpl implements ValueEntityService{

    private ValueEntityRepository valueEntityRepository;

    static ValueEntityMapper valueEntityMapper = Mappers.getMapper(ValueEntityMapper.class);

    public ValueEntityServiceImpl(ValueEntityRepository valueEntityRepository) {
        this.valueEntityRepository = valueEntityRepository;
    }


    @Override
    public ValueEntity getValueEntityById(Long valueEntityId) {
        return valueEntityRepository.findById(valueEntityId).orElseThrow(() -> new RuntimeException("No value found with id "+valueEntityId));
    }

    @Override
    public ValueEntityDTO getValueEntityDTOById(Long valueEntityId) {
        ValueEntity valueEntity = valueEntityRepository.findById(valueEntityId).orElseThrow(() -> new RuntimeException("No value found with id "+valueEntityId));
        return valueEntityMapper.toDTO(valueEntity);
    }

    @Override
    public List<ValueEntityDTO> getAllValues() {
        return valueEntityRepository.findAll().stream().map(valueEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public ValueEntityDTO addValue(ValueEntityDTO valueDTO) {
        if (valueDTO.getId() != null) {
            throw new RuntimeException("A new value cannot already have an ID");
        }
        ValueEntity valueEntity = valueEntityMapper.toEntity(valueDTO);
        valueEntity = valueEntityRepository.save(valueEntity);
        return valueEntityMapper.toDTO(valueEntity);
    }

    @Override
    @Transactional
    public ValueEntityDTO updateValue(ValueEntityDTO valueDTO) {
        ValueEntity existingBatchEntity = valueEntityRepository.findById(valueDTO.getId()).orElseThrow(() -> new RuntimeException("No value found with id "+valueDTO.getId()));
        valueEntityMapper.updateValueFromDTO(valueDTO, existingBatchEntity);
        return valueEntityMapper.toDTO(valueEntityRepository.save(existingBatchEntity));
    }

    @Override
    @Transactional
    public void deleteValue(Long id) {
        valueEntityRepository.deleteById(id);
    }
}
