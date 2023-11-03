package com.emt.med.field;

import com.emt.med.batch.BatchEntity;
import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.batch.BatchEntityMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring")
public interface FieldEntityMapper {

    FieldEntityMapper INSTANCE = Mappers.getMapper( FieldEntityMapper.class );
    FieldEntityDTO toDTO(FieldEntity fieldEntity);
    FieldEntity toEntity(FieldEntityDTO fieldEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFieldFromDTO(FieldEntityDTO dto, @MappingTarget FieldEntity entity);
}
