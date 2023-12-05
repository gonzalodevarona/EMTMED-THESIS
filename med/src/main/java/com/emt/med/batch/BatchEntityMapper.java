package com.emt.med.batch;

import com.emt.med.consumable.ConsumableEntityMapper;
import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicationBatch.MedicationBatchEntityDTO;
import com.emt.med.medicine.MedicineEntityMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ConsumableEntityMapper.class, OrderEntityMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class, LocationMapper.class})
public interface BatchEntityMapper {

    BatchEntityMapper INSTANCE = Mappers.getMapper( BatchEntityMapper.class );

    @Mapping(target = "consumable", ignore = true)
    BatchEntityDTO toDTO(BatchEntity batchEntity);

    @Mapping(target = "consumable", ignore = true)
    BatchEntity toEntity(BatchEntityDTO batchEntityDTO);

    List<BatchEntityDTO> mapToDTO(List<BatchEntity> batches);

    List<BatchEntity> map(List<BatchEntityDTO> batches);
}
