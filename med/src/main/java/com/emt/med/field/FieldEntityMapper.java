package com.emt.med.field;

import com.emt.med.value.ValueEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( uses = ValueEntityMapper.class)
public interface FieldEntityMapper {

    FieldEntityMapper INSTANCE = Mappers.getMapper( FieldEntityMapper.class );

    FieldEntityDTO toDTO(FieldEntity fieldEntity);

    FieldEntity toEntity(FieldEntityDTO fieldEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFieldFromDTO(FieldEntityDTO dto, @MappingTarget FieldEntity entity);
}