package com.emt.med.value;

import com.emt.med.field.FieldEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( uses = FieldEntityMapper.class)
public interface ValueEntityMapper {


    ValueEntityMapper INSTANCE = Mappers.getMapper( ValueEntityMapper.class );

    ValueEntityDTO toDTO(ValueEntity valueEntity);
    ValueEntity toEntity(ValueEntityDTO valueEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateValueFromDTO(ValueEntityDTO dto, @MappingTarget ValueEntity entity);


}