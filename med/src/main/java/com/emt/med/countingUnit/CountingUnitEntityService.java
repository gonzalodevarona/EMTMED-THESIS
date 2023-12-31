package com.emt.med.countingUnit;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountingUnitEntityService {

    CountingUnitEntity getCountingUnitById(Long countingUnitEntityId);

    CountingUnitEntityDTO getCountingUnitDTOById(Long countingUnitEntityId);

    List<CountingUnitEntityDTO> getAllCountingUnits();
    CountingUnitEntityDTO addCountingUnit(CountingUnitEntityDTO countingUnitEntityDTO);

    CountingUnitEntity saveCountingUnit(CountingUnitEntity countingUnit);

    CountingUnitEntityDTO updateCountingUnit(CountingUnitEntityDTO countingUnitEntityDTO);

    void deleteCountingUnit(Long id);
}
