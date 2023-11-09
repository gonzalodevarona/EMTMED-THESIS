package com.emt.med.weightUnit;

import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = SupplyMapper.class)
public interface WeightUnitEntityMapper {

    WeightUnitEntityMapper INSTANCE = Mappers.getMapper( WeightUnitEntityMapper.class );

//    @Mapping(target = "supplyDTOList", ignore = true)
//    WeightUnitEntityDTO toDTOExcludeSupplyDTOList(WeightUnitEntity weightUnitEntity);

    @Mapping(target = "supplyDTOList", ignore = true)
    WeightUnitEntityDTO toDTO(WeightUnitEntity weightUnitEntity);

    @InheritInverseConfiguration
    WeightUnitEntity toEntity(WeightUnitEntityDTO weightUnitEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "supplyList", ignore = true)
    void updateWeightUnitFromDTO(WeightUnitEntityDTO dto, @MappingTarget WeightUnitEntity entity);
}
