package com.emt.med.inventoryOrder;

import com.emt.med.batch.BatchEntityMapper;
import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.medicationBatch.MedicationBatchEntityMapper;
import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SupplyMapper.class, LocationMapper.class, CountingUnitEntityMapper.class, MedicationBatchEntityMapper.class, BatchEntityMapper.class})
public interface InventoryOrderEntityMapper {
    InventoryOrderEntityMapper INSTANCE = Mappers.getMapper( InventoryOrderEntityMapper.class );


    @Mapping(target = "destination.destinationList", ignore = true)
    @Mapping(target = "destination.medicationBatchList", ignore = true)
    @Mapping(target = "destination.batchList", ignore = true)
    InventoryOrderEntityDTO toDTO(InventoryOrderEntity inventoryOrderEntity);


    @Mapping(target = "destination.destinationList", ignore = true)
    @Mapping(target = "destination.medicationBatchList", ignore = true)
    @Mapping(target = "destination.batchList", ignore = true)
    InventoryOrderEntity toEntity(InventoryOrderEntityDTO inventoryOrderEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInventoryOrderFromDTO(InventoryOrderEntityDTO dto, @MappingTarget InventoryOrderEntity entity);
}
