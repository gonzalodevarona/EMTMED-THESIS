package com.emt.med.supply;

import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.countingUnit.CountingUnitEntityService;
import com.emt.med.location.LocationService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SupplyServiceImpl implements SupplyService{

    static SupplyMapper supplyMapper = Mappers.getMapper(SupplyMapper.class);


    // *** Counting Unit ***
    private CountingUnitEntityService countingUnitEntityService;

    // *** Location ***
    private LocationService locationService;

    public SupplyServiceImpl(CountingUnitEntityService countingUnitEntityService, LocationService locationService) {
        this.countingUnitEntityService = countingUnitEntityService;
        this.locationService = locationService;
    }


    @Override
    @Transactional
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
    @Transactional
    public Supply removeCountingUnitFromSupply(Supply supply) {
        CountingUnitEntity countingUnitEntity = supply.getCountingUnit();

        countingUnitEntity.getSupplyList().remove(supply);

        supply.setCountingUnit(null);
        countingUnitEntityService.saveCountingUnit(countingUnitEntity);
        return supply;
    }


    @Override
    public Supply addRelationships(Supply supply) {
        supply.setCountingUnit(countingUnitEntityService.getCountingUnitById(supply.getCountingUnit().getId()));
        return supply;
    }

}
