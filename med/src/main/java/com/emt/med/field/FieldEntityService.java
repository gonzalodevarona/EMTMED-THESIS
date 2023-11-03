package com.emt.med.field;

import com.emt.med.batch.BatchEntityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FieldEntityService {
    FieldEntityDTO getFieldEntityById(Long fieldEntityId);
    List<FieldEntityDTO> getAllFields();
    FieldEntityDTO addField(FieldEntityDTO fieldEntityDTO);

    FieldEntityDTO updateField(FieldEntityDTO fieldEntityDTO);

    void deleteField(Long id);
}
