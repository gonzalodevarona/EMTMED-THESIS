package com.emt.med.disposalStation;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DisposalStationEntityMapper {

    DisposalStationEntityMapper INSTANCE = Mappers.getMapper( DisposalStationEntityMapper.class );

    @Mapping(target = "supplyDTOList", ignore = true)
    DisposalStationEntityDTO toDTO(DisposalStationEntity disposalStationEntity);

    @Mapping(target = "supplyList", ignore = true)
    DisposalStationEntity toEntity(DisposalStationEntityDTO disposalStationEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "supplyList", ignore = true)
    void updateDisposalStationFromDTO(DisposalStationEntityDTO dto, @MappingTarget DisposalStationEntity entity);
}
