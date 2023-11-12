package com.emt.med.batch;

import com.emt.med.consumable.ConsumableEntityMapper;
import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.medicine.MedicineEntityMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ConsumableEntityMapper.class, OrderEntityMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class})
public interface BatchEntityMapper {

    BatchEntityMapper INSTANCE = Mappers.getMapper( BatchEntityMapper.class );

    @Mapping(target = "consumable", ignore = true)
    BatchEntityDTO toDTO(BatchEntity batchEntity);

    @Mapping(target = "consumable", ignore = true)
    BatchEntity toEntity(BatchEntityDTO batchEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBatchFromDto(BatchEntityDTO dto, @MappingTarget BatchEntity entity);

    List<BatchEntityDTO> map(List<BatchEntity> batches);
}
