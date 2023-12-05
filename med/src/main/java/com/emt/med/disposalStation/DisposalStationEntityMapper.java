package com.emt.med.disposalStation;

import com.emt.med.location.LocationMapper;
import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SupplyMapper.class, LocationMapper.class})
public interface DisposalStationEntityMapper {

    DisposalStationEntityMapper INSTANCE = Mappers.getMapper( DisposalStationEntityMapper.class );

    @Mapping(target = "medicationBatchList", ignore = true)
    @Mapping(target = "batchList", ignore = true)
    DisposalStationEntityDTO toDTO(DisposalStationEntity disposalStationEntity);

    @Mapping(target = "medicationBatchList", ignore = true)
    @Mapping(target = "batchList", ignore = true)
    DisposalStationEntity toEntity(DisposalStationEntityDTO disposalStationEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "medicationBatchList", ignore = true)
    @Mapping(target = "batchList", ignore = true)
    void updateDisposalStationFromDTO(DisposalStationEntityDTO dto, @MappingTarget DisposalStationEntity entity);
}
