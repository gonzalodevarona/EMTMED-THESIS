package com.emt.med.batch;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring")
public interface BatchEntityMapper {

    BatchEntityMapper INSTANCE = Mappers.getMapper( BatchEntityMapper.class );
    BatchEntityDTO toDTO(BatchEntity batchEntity);
    BatchEntity toEntity(BatchEntityDTO batchEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBatchFromDto(BatchEntityDTO dto, @MappingTarget BatchEntity entity);
}
