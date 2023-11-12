package com.emt.med.supply;


import com.emt.med.batch.BatchEntity;
import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.weightUnit.WeightUnitEntity;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplyService {


    // Weight Unit
    Supply addWeightUnitToSupply(WeightUnitEntity weightUnit, Supply supply);

    Supply removeWeightUnitFromSupply(Supply supply);

    // Counting Unit
    Supply addCountingUnitToSupply(CountingUnitEntity countingUnit, Supply supply);

    Supply removeCountingUnitFromSupply(Supply supply);
   

    
}
