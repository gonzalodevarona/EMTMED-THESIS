package com.emt.med.supply;


import com.emt.med.batch.BatchEntityService;

import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.countingUnit.CountingUnitEntityService;

import com.emt.med.location.Location;
import com.emt.med.location.LocationService;
import com.emt.med.medicationBatch.MedicationBatchEntityService;

import com.emt.med.weightUnit.WeightUnitEntity;
import com.emt.med.weightUnit.WeightUnitEntityService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class SupplyServiceImpl implements SupplyService{

    static SupplyMapper supplyMapper = Mappers.getMapper(SupplyMapper.class);

    // *** Weight Unit ***
    private WeightUnitEntityService weightUnitEntityService;

    // *** Counting Unit ***
    private CountingUnitEntityService countingUnitEntityService;

    // *** Location ***
    private LocationService locationService;

    public SupplyServiceImpl(WeightUnitEntityService weightUnitEntityService, CountingUnitEntityService countingUnitEntityService, LocationService locationService) {
        this.weightUnitEntityService = weightUnitEntityService;
        this.countingUnitEntityService = countingUnitEntityService;
        this.locationService = locationService;
    }

    @Override
    @Transactional
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
    @Transactional
    public Supply removeWeightUnitFromSupply(Supply supply) {

        WeightUnitEntity weightUnitEntity = supply.getWeightUnit();

        weightUnitEntity.getSupplyList().remove(supply);

        supply.setWeightUnit(null);
        weightUnitEntityService.saveWeightUnitEntity(weightUnitEntity);
        return supply;
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
        supply.setWeightUnit(weightUnitEntityService.getWeightUnitById(supply.getWeightUnit().getId()));
        supply.setLocation(locationService.getLocationById(supply.getLocation().getId()));
        return supply;
    }

}
