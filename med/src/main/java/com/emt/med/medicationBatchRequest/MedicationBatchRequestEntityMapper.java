package com.emt.med.medicationBatchRequest;

import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.medicationBatch.MedicationBatchEntityMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.supply.SupplyMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SupplyMapper.class, OrderEntityMapper.class, LocationMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class, MedicationBatchEntityMapper.class})
public interface MedicationBatchRequestEntityMapper {

    MedicationBatchRequestEntityMapper INSTANCE = Mappers.getMapper( MedicationBatchRequestEntityMapper.class );

    MedicationBatchRequestEntityDTO toDTO(MedicationBatchRequestEntity medicationBatchRequestEntity);

    MedicationBatchRequestEntity toEntity(MedicationBatchRequestEntityDTO medicationBatchRequestEntityDTO);

}
