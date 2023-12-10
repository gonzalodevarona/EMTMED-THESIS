package com.emt.med.countingUnit;

import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( uses = SupplyMapper.class)
public interface    CountingUnitEntityMapper {

    CountingUnitEntityMapper INSTANCE = Mappers.getMapper( CountingUnitEntityMapper.class );
    @Mapping(target = "supplyDTOList", ignore = true)
    CountingUnitEntityDTO toDTO(CountingUnitEntity countingUnitEntity);

    @Mapping(target = "supplyList", ignore = true)
    CountingUnitEntity toEntity(CountingUnitEntityDTO countingUnitEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "supplyList", ignore = true)
    void updateCountingUnitFromDTO(CountingUnitEntityDTO dto, @MappingTarget CountingUnitEntity entity);
}
