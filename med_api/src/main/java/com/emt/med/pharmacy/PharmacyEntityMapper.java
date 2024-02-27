package com.emt.med.pharmacy;

import com.emt.med.inventoryOrder.InventoryOrderEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SupplyMapper.class, LocationMapper.class, InventoryOrderEntityMapper.class})
public interface PharmacyEntityMapper {

    PharmacyEntityMapper INSTANCE = Mappers.getMapper( PharmacyEntityMapper.class );

    @Mapping(target = "medicationBatchList", ignore = true)
    @Mapping(target = "batchList", ignore = true)
    PharmacyEntityDTO toDTO(PharmacyEntity pharmacyEntity);

    @Mapping(target = "medicationBatchList", ignore = true)
    @Mapping(target = "batchList", ignore = true)
    PharmacyEntity toEntity(PharmacyEntityDTO pharmacyEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "medicationBatchList", ignore = true)
    @Mapping(target = "batchList", ignore = true)
    void updatePharmacyFromDTO(PharmacyEntityDTO dto, @MappingTarget PharmacyEntity entity);
}
