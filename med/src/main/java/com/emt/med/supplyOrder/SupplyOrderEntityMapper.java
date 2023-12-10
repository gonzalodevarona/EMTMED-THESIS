package com.emt.med.supplyOrder;

import com.emt.med.batch.BatchEntityMapper;
import com.emt.med.batchRequest.BatchRequestEntityMapper;
import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.medicationBatch.MedicationBatchEntityMapper;
import com.emt.med.medicationBatchRequest.MedicationBatchRequestEntityMapper;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.supply.SupplyMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SupplyMapper.class, LocationMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class, MedicationBatchRequestEntityMapper.class, BatchRequestEntityMapper.class, MedicationBatchEntityMapper.class, BatchEntityMapper.class})
public interface SupplyOrderEntityMapper {

    SupplyOrderEntityMapper INSTANCE = Mappers.getMapper( SupplyOrderEntityMapper.class );
    SupplyOrderEntityDTO toDTO(SupplyOrderEntity supplyOrderEntity);


    SupplyOrderEntity toEntity(SupplyOrderEntityDTO supplyOrderEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSupplyOrderFromDto(SupplyOrderEntityDTO dto, @MappingTarget SupplyOrderEntity entity);
}
