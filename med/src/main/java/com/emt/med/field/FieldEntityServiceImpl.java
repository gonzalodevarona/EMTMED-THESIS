package com.emt.med.field;

import com.emt.med.batch.BatchEntity;
import com.emt.med.batch.BatchEntityMapper;
import com.emt.med.value.ValueEntity;
import com.emt.med.value.ValueEntityDTO;
import com.emt.med.value.ValueEntityMapper;
import com.emt.med.value.ValueEntityService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldEntityServiceImpl implements FieldEntityService{

    private FieldEntityRepository fieldEntityRepository;

    private ValueEntityService valueEntityService;
    static FieldEntityMapper fieldEntityMapper = Mappers.getMapper(FieldEntityMapper.class);

    public FieldEntityServiceImpl(FieldEntityRepository fieldEntityRepository, ValueEntityService valueEntityService) {
        this.fieldEntityRepository = fieldEntityRepository;
        this.valueEntityService = valueEntityService;
    }

    @Override
    public FieldEntity getFieldEntityById(Long fieldEntityId) {
        return fieldEntityRepository.findById(fieldEntityId).orElseThrow(() -> new RuntimeException("No field found with id "+fieldEntityId));
    }

    @Override
    public FieldEntityDTO getFieldEntityDTOById(Long fieldEntityId) {
        FieldEntity fieldEntity = fieldEntityRepository.findById(fieldEntityId).orElseThrow(() -> new RuntimeException("No field found with id "+fieldEntityId));
        return fieldEntityMapper.toDTO(fieldEntity);
    }

    @Override
    public List<FieldEntityDTO> getAllFields() {
        return fieldEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "type")).stream().map(fieldEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public FieldEntityDTO addField(FieldEntityDTO fieldEntityDTO) {
        if (fieldEntityDTO.getId() != null) {
            throw new RuntimeException("A new field cannot already have an ID");
        }
        FieldEntity fieldEntity = fieldEntityMapper.toEntity(fieldEntityDTO);
        fieldEntity = fieldEntityRepository.save(fieldEntity);
        return fieldEntityMapper.toDTO(fieldEntity);
    }

    @Override
    @Transactional
    public FieldEntityDTO updateField(FieldEntityDTO fieldEntityDTO) {
        FieldEntity existingFieldEntity = fieldEntityRepository.findById(fieldEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No field found with id "+fieldEntityDTO.getId()));
        fieldEntityMapper.updateFieldFromDTO(fieldEntityDTO, existingFieldEntity);
        return fieldEntityMapper.toDTO(fieldEntityRepository.save(existingFieldEntity));
    }

    @Override
    @Transactional
    public void deleteField(Long id) {
        fieldEntityRepository.deleteById(id);
    }

    @Override
    @Transactional
    public FieldEntityDTO addValueToFieldById(Long id, ValueEntityDTO valueEntityDTO){
        FieldEntity existingFieldEntity = fieldEntityRepository.findById(id).orElseThrow(() -> new RuntimeException("No field found with id "+id));
        valueEntityDTO = valueEntityService.addValue(valueEntityDTO);

        FieldEntityDTO  existingFieldEntityDTO = fieldEntityMapper.toDTO(existingFieldEntity);

        existingFieldEntityDTO.getValues().add(valueEntityDTO);

        return updateField(existingFieldEntityDTO);
    }

    @Override
    @Transactional
    public FieldEntityDTO updateValueFromFieldById(Long id, ValueEntityDTO valueEntityDTO) {
        FieldEntity existingFieldEntity = fieldEntityRepository.findById(id).orElseThrow(() -> new RuntimeException("No field found with id "+id));

        valueEntityService.updateValue(valueEntityDTO);

        return updateField(fieldEntityMapper.toDTO(existingFieldEntity));
    }

    @Override
    @Transactional
    public void deleteValueFromFieldById(Long idField, Long idValue){
        FieldEntity existingFieldEntity = fieldEntityRepository.findById(idField).orElseThrow(() -> new RuntimeException("No field found with id "+idField));
        ValueEntity existingValueEntity = valueEntityService.getValueEntityById(idValue);
        existingFieldEntity.getValues().remove(existingValueEntity);

        updateField(fieldEntityMapper.toDTO(existingFieldEntity));
        valueEntityService.deleteValue(idValue);


    }
}
