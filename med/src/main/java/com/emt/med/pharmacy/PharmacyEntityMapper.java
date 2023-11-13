package com.emt.med.pharmacy;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PharmacyEntityMapper {

    PharmacyEntityMapper INSTANCE = Mappers.getMapper( PharmacyEntityMapper.class );

    @Mapping(target = "supplyDTOList", ignore = true)
    PharmacyEntityDTO toDTO(PharmacyEntity pharmacyEntity);

    @Mapping(target = "supplyList", ignore = true)
    PharmacyEntity toEntity(PharmacyEntityDTO pharmacyEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "supplyList", ignore = true)
    void updatePharmacyFromDTO(PharmacyEntityDTO dto, @MappingTarget PharmacyEntity entity);
}
