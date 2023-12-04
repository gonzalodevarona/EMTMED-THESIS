package com.emt.med.consumable;

import com.emt.med.batch.BatchEntityMapper;
import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( uses = {OrderEntityMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class, BatchEntityMapper.class, LocationMapper.class})
public interface ConsumableEntityMapper {
    ConsumableEntityMapper INSTANCE = Mappers.getMapper( ConsumableEntityMapper.class );

    @Mapping(target = "orders", ignore = true)
    ConsumableEntityDTO toDTO(ConsumableEntity consumableEntity);

    @Named("toDTONoOrdersNoBatches")
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "batches", ignore = true)
    ConsumableEntityDTO toDTONoOrdersNoBatches(ConsumableEntity consumableEntity);


    @Mapping(target = "orders", ignore = true)
    ConsumableEntity toEntity(ConsumableEntityDTO consumableEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConsumableFromDTO(ConsumableEntityDTO dto, @MappingTarget ConsumableEntity entity);

    List<ConsumableEntityDTO> toConsumableDTOList(List<ConsumableEntity> consumables);

    List<ConsumableEntity> toConsumableList(List<ConsumableEntityDTO> consumablesDto);
}
