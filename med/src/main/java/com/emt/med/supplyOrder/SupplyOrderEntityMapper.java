package com.emt.med.supplyOrder;

import com.emt.med.location.LocationMapper;
import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SupplyMapper.class})
public interface SupplyOrderEntityMapper {

    SupplyOrderEntityMapper INSTANCE = Mappers.getMapper( SupplyOrderEntityMapper.class );
    SupplyOrderEntityDTO toDTO(SupplyOrderEntity supplyOrderEntity);

    SupplyOrderEntity toEntity(SupplyOrderEntityDTO supplyOrderEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSupplyOrderFromDto(SupplyOrderEntityDTO dto, @MappingTarget SupplyOrderEntity entity);
}
