package com.emt.med.value;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ValueEntityService {

    ValueEntityDTO getValueEntityDTOById(Long valueEntityId);

    ValueEntity getValueEntityById(Long valueEntityId);
    List<ValueEntityDTO> getAllValues();
    ValueEntityDTO addValue(ValueEntityDTO valueDTO);

    ValueEntityDTO updateValue(ValueEntityDTO valueDTO);

    void deleteValue(Long id);
}
