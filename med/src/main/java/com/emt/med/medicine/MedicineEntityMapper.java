package com.emt.med.medicine;

import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.medicationBatch.MedicationBatchEntityMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.supply.SupplyMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper( uses = {SupplyMapper.class, OrderEntityMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class, MedicationBatchEntityMapper.class, LocationMapper.class})
public interface MedicineEntityMapper {
    MedicineEntityMapper INSTANCE = Mappers.getMapper( MedicineEntityMapper.class );

    @Mapping(target = "inventoryOrders", ignore = true)
    MedicineEntityDTO toDTO(MedicineEntity medicineEntity);

    @Named("toDTONoOrdersNoBatches")
    @Mapping(target = "inventoryOrders", ignore = true)
    @Mapping(target = "batches", ignore = true)
    MedicineEntityDTO toDTONoOrdersNoBatches(MedicineEntity medicineEntity);

    @Mapping(target = "inventoryOrders", ignore = true)
    MedicineEntity toEntity(MedicineEntityDTO medicineEntityDTO);


}
