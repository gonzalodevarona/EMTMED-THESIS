package com.emt.med.supply;

import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.location.Location;
import com.emt.med.weightUnit.WeightUnitEntity;
import org.springframework.stereotype.Service;

@Service
public interface SupplyService {


    // Weight Unit
    Supply addWeightUnitToSupply(WeightUnitEntity weightUnit, Supply supply);

    Supply removeWeightUnitFromSupply(Supply supply);

    // Counting Unit
    Supply addCountingUnitToSupply(CountingUnitEntity countingUnit, Supply supply);

    Supply removeCountingUnitFromSupply(Supply supply);

    Supply addRelationships(Supply supply);


   

    
}
