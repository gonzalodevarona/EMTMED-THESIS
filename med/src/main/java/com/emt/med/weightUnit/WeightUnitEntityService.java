package com.emt.med.weightUnit;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeightUnitEntityService {

    WeightUnitEntityDTO getWeightUnitById(Long weightUnitEntityId);
    List<WeightUnitEntityDTO> getAllWeightUnits();
    WeightUnitEntityDTO addWeightUnit(WeightUnitEntityDTO weightUnitEntityDTO);

    WeightUnitEntity saveWeightUnitEntity(WeightUnitEntity weightUnit);

    WeightUnitEntityDTO updateWeightUnit(WeightUnitEntityDTO weightUnitEntityDTO);

    void deleteWeightUnit(Long id);
}
