package com.emt.med.supply;

import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.countingUnit.CountingUnitEntityService;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityMapper;
import com.emt.med.weightUnit.WeightUnitEntity;
import com.emt.med.weightUnit.WeightUnitEntityService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SupplyServiceImpl implements SupplyService{

    static SupplyMapper supplyMapper = Mappers.getMapper(SupplyMapper.class);

    private WeightUnitEntityService weightUnitEntityService;

    private CountingUnitEntityService countingUnitEntityService;

    public SupplyServiceImpl(WeightUnitEntityService weightUnitEntityService, CountingUnitEntityService countingUnitEntityService) {
        this.weightUnitEntityService = weightUnitEntityService;
        this.countingUnitEntityService = countingUnitEntityService;
    }

    @Override
    public Supply addWeightUnitToSupply(WeightUnitEntity weightUnit, Supply supply) {
        if (weightUnit == null){
            throw new RuntimeException("Error: In order to add a medicine, a weight unit must be added or assigned to it");
        } else if (weightUnit.getSupplyList() == null){
            weightUnit.setSupplyList(new ArrayList<Supply>());
        } else if (weightUnit.getId() == null){
            weightUnit = weightUnitEntityService.saveWeightUnitEntity(weightUnit);
        }

        supply.setWeightUnit(weightUnit);
        weightUnit.getSupplyList().add(supply);
        weightUnitEntityService.saveWeightUnitEntity(weightUnit);

        return supply;
    }

    @Override
    public Supply removeWeightUnitFromSupply(Supply supply) {
//        MedicineEntity medicineEntity = getMedicineEntityById(medicineEntityId);
        WeightUnitEntity weightUnitEntity = supply.getWeightUnit();

        weightUnitEntity.getSupplyList().remove(supply);

        supply.setWeightUnit(null);
        weightUnitEntityService.saveWeightUnitEntity(weightUnitEntity);
        return supply;
    }

    @Override
    public Supply addCountingUnitToSupply(CountingUnitEntity countingUnit, Supply supply) {
        if (countingUnit == null){
            throw new RuntimeException("Error: In order to add a medicine, a counting unit must be added or assigned to it");
        } else if (countingUnit.getSupplyList() == null){
            countingUnit.setSupplyList(new ArrayList<Supply>());
        } else if (countingUnit.getId() == null){
            countingUnit = countingUnitEntityService.saveCountingUnit(countingUnit);
        }

        supply.setCountingUnit(countingUnit);
        countingUnit.getSupplyList().add(supply);

        countingUnitEntityService.saveCountingUnit(countingUnit);

        return supply;
    }

    @Override
    public Supply removeCountingUnitFromSupply(Supply supply) {
        CountingUnitEntity countingUnitEntity = supply.getCountingUnit();

        countingUnitEntity.getSupplyList().remove(supply);

        supply.setCountingUnit(null);
        countingUnitEntityService.saveCountingUnit(countingUnitEntity);
        return supply;
    }
}
