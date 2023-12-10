package com.emt.med.batchRequest;

import com.emt.med.batch.BatchEntityMapper;
import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.supply.SupplyMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {SupplyMapper.class, OrderEntityMapper.class, LocationMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class, BatchEntityMapper.class})
public interface BatchRequestEntityMapper {

    BatchRequestEntityMapper INSTANCE = Mappers.getMapper( BatchRequestEntityMapper.class );

    @Mapping(target="supplyOrder", ignore = true)
    BatchRequestEntityDTO toDTO(BatchRequestEntity batchRequestEntity);
    @Mapping(target="supplyOrder", ignore = true)
    BatchRequestEntity toEntity(BatchRequestEntityDTO batchRequestEntityDTO);

    List<BatchRequestEntityDTO> mapToDTO(List<BatchRequestEntity> batchRequests);

    List<BatchRequestEntity> map(List<BatchRequestEntityDTO> batchRequests);

}
