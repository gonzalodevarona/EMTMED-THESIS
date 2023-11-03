package com.emt.med.weightUnit;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring")
public interface WeightUnitEntityMapper {

    WeightUnitEntityMapper INSTANCE = Mappers.getMapper( WeightUnitEntityMapper.class );
    WeightUnitEntityDTO toDTO(WeightUnitEntity weightUnitEntity);
    WeightUnitEntity toEntity(WeightUnitEntityDTO weightUnitEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateWeightUnitFromDTO(WeightUnitEntityDTO dto, @MappingTarget WeightUnitEntity entity);
}
