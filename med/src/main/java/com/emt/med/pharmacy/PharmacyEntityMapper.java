package com.emt.med.pharmacy;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PharmacyEntityMapper {

    PharmacyEntityMapper INSTANCE = Mappers.getMapper( PharmacyEntityMapper.class );
    PharmacyEntityDTO toDTO(PharmacyEntity pharmacyEntity);
    PharmacyEntity toEntity(PharmacyEntityDTO pharmacyEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePharmacyFromDTO(PharmacyEntityDTO dto, @MappingTarget PharmacyEntity entity);
}
