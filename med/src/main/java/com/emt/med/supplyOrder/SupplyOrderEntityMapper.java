package com.emt.med.supplyOrder;

import com.emt.med.location.LocationMapper;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SupplyMapper.class})
public interface SupplyOrderEntityMapper {

    SupplyOrderEntityMapper INSTANCE = Mappers.getMapper( SupplyOrderEntityMapper.class );
    SupplyOrderEntityDTO toDTO(SupplyOrderEntity supplyOrderEntity);

    @Named("toDTONoSupplies")
    @Mapping(target = "supplies", ignore = true)
    SupplyOrderEntityDTO toDTONoSupplies(SupplyOrderEntity supplyOrderEntity);

    @Named("toEntityNoSupplies")
    @Mapping(target = "supplies", ignore = true)
    SupplyOrderEntity toEntityNoSupplies(SupplyOrderEntityDTO supplyOrderEntity);

    SupplyOrderEntity toEntity(SupplyOrderEntityDTO supplyOrderEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSupplyOrderFromDto(SupplyOrderEntityDTO dto, @MappingTarget SupplyOrderEntity entity);
}
