package com.emt.med.value;

import com.emt.med.field.FieldEntity;
import com.emt.med.field.FieldEntityDTO;
import com.emt.med.field.FieldEntityMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring")
public interface ValueEntityMapper {


    ValueEntityMapper INSTANCE = Mappers.getMapper( ValueEntityMapper.class );
    ValueEntityDTO toDTO(ValueEntity valueEntity);
    ValueEntity toEntity(ValueEntityDTO valueEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateValueFromDTO(ValueEntityDTO dto, @MappingTarget ValueEntity entity);
}
