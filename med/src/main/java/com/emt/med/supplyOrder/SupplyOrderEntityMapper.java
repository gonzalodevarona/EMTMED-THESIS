package com.emt.med.supplyOrder;

import com.emt.med.supply.SupplyMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( uses = SupplyMapper.class)
public interface SupplyOrderEntityMapper {

    SupplyOrderEntityMapper INSTANCE = Mappers.getMapper( SupplyOrderEntityMapper.class );
    SupplyOrderEntityDTO toDTO(SupplyOrderEntity supplyOrderEntity);
    SupplyOrderEntity toEntity(SupplyOrderEntityDTO supplyOrderEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSupplyOrderFromDto(SupplyOrderEntityDTO dto, @MappingTarget SupplyOrderEntity entity);
}
