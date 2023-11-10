package com.emt.med.countingUnit;

import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicationBatch.MedicationBatchEntityDTO;
import com.emt.med.medicationBatch.MedicationBatchEntityMapper;
import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( uses = SupplyMapper.class)
public interface CountingUnitEntityMapper {

    CountingUnitEntityMapper INSTANCE = Mappers.getMapper( CountingUnitEntityMapper.class );
    @Mapping(target = "supplyDTOList", ignore = true)
    CountingUnitEntityDTO toDTO(CountingUnitEntity countingUnitEntity);
    @InheritInverseConfiguration
    CountingUnitEntity toEntity(CountingUnitEntityDTO countingUnitEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "supplyList", ignore = true)
    void updateCountingUnitFromDTO(CountingUnitEntityDTO dto, @MappingTarget CountingUnitEntity entity);
}
