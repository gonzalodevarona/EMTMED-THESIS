package com.emt.med.inventoryOrder;

import com.emt.med.supply.SupplyMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( uses = SupplyMapper.class)
public interface InventoryOrderEntityMapper {
    InventoryOrderEntityMapper INSTANCE = Mappers.getMapper( InventoryOrderEntityMapper.class );
    InventoryOrderEntityDTO toDTO(InventoryOrderEntity inventoryOrderEntity);
    InventoryOrderEntity toEntity(InventoryOrderEntityDTO inventoryOrderEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInventoryOrderFromDTO(InventoryOrderEntityDTO dto, @MappingTarget InventoryOrderEntity entity);
}
