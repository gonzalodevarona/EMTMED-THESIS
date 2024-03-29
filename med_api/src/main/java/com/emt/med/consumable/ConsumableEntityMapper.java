package com.emt.med.consumable;

import com.emt.med.batch.BatchEntityMapper;
import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( uses = {SupplyMapper.class, OrderEntityMapper.class, CountingUnitEntityMapper.class, BatchEntityMapper.class, LocationMapper.class})
public interface ConsumableEntityMapper {
    ConsumableEntityMapper INSTANCE = Mappers.getMapper( ConsumableEntityMapper.class );

    ConsumableEntityDTO toDTO(ConsumableEntity consumableEntity);

    @Named("toDTONoBatches")
    @Mapping(target = "batches", ignore = true)
    ConsumableEntityDTO toDTONoBatches(ConsumableEntity consumableEntity);


    ConsumableEntity toEntity(ConsumableEntityDTO consumableEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConsumableFromDTO(ConsumableEntityDTO dto, @MappingTarget ConsumableEntity entity);

    List<ConsumableEntityDTO> toConsumableDTOList(List<ConsumableEntity> consumables);

    List<ConsumableEntity> toConsumableList(List<ConsumableEntityDTO> consumablesDto);
}
