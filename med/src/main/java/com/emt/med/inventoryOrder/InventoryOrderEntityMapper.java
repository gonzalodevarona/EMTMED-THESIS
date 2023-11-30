package com.emt.med.inventoryOrder;

import com.emt.med.disposalStation.DisposalStationEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.pharmacy.PharmacyEntityMapper;
import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SupplyMapper.class, LocationMapper.class})
public interface InventoryOrderEntityMapper {
    InventoryOrderEntityMapper INSTANCE = Mappers.getMapper( InventoryOrderEntityMapper.class );

    @Mapping(target = "destination.sourceList", ignore = true)
    @Mapping(target = "destination.destinationList", ignore = true)
    @Mapping(target = "destination.supplyList", ignore = true)
    @Mapping(target = "source.sourceList", ignore = true)
    @Mapping(target = "source.destinationList", ignore = true)
    @Mapping(target = "source.supplyList", ignore = true)
    InventoryOrderEntityDTO toDTO(InventoryOrderEntity inventoryOrderEntity);

    @Mapping(target = "destination.sourceList", ignore = true)
    @Mapping(target = "destination.destinationList", ignore = true)
    @Mapping(target = "destination.supplyList", ignore = true)
    @Mapping(target = "source.sourceList", ignore = true)
    @Mapping(target = "source.destinationList", ignore = true)
    @Mapping(target = "source.supplyList", ignore = true)
    InventoryOrderEntity toEntity(InventoryOrderEntityDTO inventoryOrderEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInventoryOrderFromDTO(InventoryOrderEntityDTO dto, @MappingTarget InventoryOrderEntity entity);
}
