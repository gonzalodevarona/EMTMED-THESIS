package com.emt.med.disposalStation;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DisposalStationEntityMapper {

    DisposalStationEntityMapper INSTANCE = Mappers.getMapper( DisposalStationEntityMapper.class );
    DisposalStationEntityDTO toDTO(DisposalStationEntity disposalStationEntity);
    DisposalStationEntity toEntity(DisposalStationEntityDTO disposalStationEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDisposalStationFromDTO(DisposalStationEntityDTO dto, @MappingTarget DisposalStationEntity entity);
}
