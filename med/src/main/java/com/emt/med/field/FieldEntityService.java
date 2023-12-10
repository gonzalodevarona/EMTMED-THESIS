package com.emt.med.field;

import com.emt.med.value.ValueEntityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FieldEntityService {
    FieldEntity getFieldEntityById(Long fieldEntityId);
    FieldEntityDTO getFieldEntityDTOById(Long fieldEntityId);
    List<FieldEntityDTO> getAllFields();
    FieldEntityDTO addField(FieldEntityDTO fieldEntityDTO);

    FieldEntityDTO addValueToFieldById(Long id, ValueEntityDTO valueEntityDTO);
    FieldEntityDTO updateValueFromFieldById(Long id, ValueEntityDTO valueEntityDTO);

    void deleteValueFromFieldById(Long idField, Long idValue);

    FieldEntityDTO updateField(FieldEntityDTO fieldEntityDTO);

    void deleteField(Long id);
}
