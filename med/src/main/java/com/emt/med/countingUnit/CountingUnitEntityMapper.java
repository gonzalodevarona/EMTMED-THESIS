package com.emt.med.countingUnit;

import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicationBatch.MedicationBatchEntityDTO;
import com.emt.med.medicationBatch.MedicationBatchEntityMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring")
public interface CountingUnitEntityMapper {

    CountingUnitEntityMapper INSTANCE = Mappers.getMapper( CountingUnitEntityMapper.class );
    CountingUnitEntityDTO toDTO(CountingUnitEntity countingUnitEntity);
    CountingUnitEntity toEntity(CountingUnitEntityDTO countingUnitEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCountingUnitFromDTO(CountingUnitEntityDTO dto, @MappingTarget CountingUnitEntity entity);
}
