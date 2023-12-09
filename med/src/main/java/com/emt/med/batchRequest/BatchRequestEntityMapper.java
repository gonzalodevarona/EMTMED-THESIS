package com.emt.med.batchRequest;

import com.emt.med.batch.BatchEntityMapper;
import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.supply.SupplyMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SupplyMapper.class, OrderEntityMapper.class, LocationMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class, BatchEntityMapper.class})
public interface BatchRequestEntityMapper {

    BatchRequestEntityMapper INSTANCE = Mappers.getMapper( BatchRequestEntityMapper.class );

    BatchRequestEntityDTO toDTO(BatchRequestEntity batchRequestEntity);

    BatchRequestEntity toEntity(BatchRequestEntityDTO batchRequestEntityDTO);

}
