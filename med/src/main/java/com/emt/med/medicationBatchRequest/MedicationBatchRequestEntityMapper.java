package com.emt.med.medicationBatchRequest;

import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.medicationBatch.MedicationBatchEntityMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.supply.SupplyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {SupplyMapper.class, OrderEntityMapper.class, LocationMapper.class, CountingUnitEntityMapper.class, MedicationBatchEntityMapper.class})
public interface MedicationBatchRequestEntityMapper {

    MedicationBatchRequestEntityMapper INSTANCE = Mappers.getMapper( MedicationBatchRequestEntityMapper.class );

    @Mapping(target="supplyOrder", ignore = true)
    MedicationBatchRequestEntityDTO toDTO(MedicationBatchRequestEntity medicationBatchRequestEntity);

    @Mapping(target="supplyOrder", ignore = true)
    MedicationBatchRequestEntity toEntity(MedicationBatchRequestEntityDTO medicationBatchRequestEntityDTO);

    List<MedicationBatchRequestEntityDTO> mapToDTO(List<MedicationBatchRequestEntity> medicationBatchRequests);

    List<MedicationBatchRequestEntity> map(List<MedicationBatchRequestEntityDTO> medicationBatchRequests);

}
