package com.emt.med.medicationBatch;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicationBatchEntityMapper {

    MedicationBatchEntityMapper INSTANCE = Mappers.getMapper( MedicationBatchEntityMapper.class );
    MedicationBatchEntityDTO toDTO(MedicationBatchEntity medicationBatchEntity);
    MedicationBatchEntity toEntity(MedicationBatchEntityDTO medicationBatchEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMedicationBatchFromDto(MedicationBatchEntityDTO dto, @MappingTarget MedicationBatchEntity entity);
}
