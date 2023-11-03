package com.emt.med.countingUnit;

import com.emt.med.batch.BatchEntityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountingUnitEntityService {

    CountingUnitEntityDTO getCountingUnitById(Long countingUnitEntityId);
    List<CountingUnitEntityDTO> getAllCountingUnits();
    CountingUnitEntityDTO addCountingUnit(CountingUnitEntityDTO countingUnitEntityDTO);

    CountingUnitEntityDTO updateCountingUnit(CountingUnitEntityDTO countingUnitEntityDTO);

    void deleteCountingUnit(Long id);
}
