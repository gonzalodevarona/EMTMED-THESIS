package com.emt.med.field;

import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.value.ValueEntityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FieldEntityService {
    FieldEntityDTO getFieldEntityById(Long fieldEntityId);
    List<FieldEntityDTO> getAllFields();
    FieldEntityDTO addField(FieldEntityDTO fieldEntityDTO);

    FieldEntityDTO addValueToFieldById(Long id, ValueEntityDTO valueEntityDTO);

    FieldEntityDTO updateField(FieldEntityDTO fieldEntityDTO);

    void deleteField(Long id);
}
