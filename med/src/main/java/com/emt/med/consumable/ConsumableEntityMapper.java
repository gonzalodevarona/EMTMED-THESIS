package com.emt.med.consumable;

import com.emt.med.batch.BatchEntityMapper;
import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( uses = {OrderEntityMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class, BatchEntityMapper.class,})
public interface ConsumableEntityMapper {
    ConsumableEntityMapper INSTANCE = Mappers.getMapper( ConsumableEntityMapper.class );

    ConsumableEntityDTO toDTO(ConsumableEntity consumableEntity);

    ConsumableEntity toEntity(ConsumableEntityDTO consumableEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConsumableFromDTO(ConsumableEntityDTO dto, @MappingTarget ConsumableEntity entity);
}
