package com.emt.med.supply;

import com.emt.med.countingUnit.CountingUnitEntity;
import org.springframework.stereotype.Service;

@Service
public interface SupplyService {


    // Counting Unit
    Supply addCountingUnitToSupply(CountingUnitEntity countingUnit, Supply supply);

    Supply removeCountingUnitFromSupply(Supply supply);

    Supply addRelationships(Supply supply);


   

    
}
