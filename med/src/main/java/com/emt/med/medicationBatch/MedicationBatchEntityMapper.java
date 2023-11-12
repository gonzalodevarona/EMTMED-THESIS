package com.emt.med.medicationBatch;

import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.medicine.MedicineEntityMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.supply.SupplyMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {MedicineEntityMapper.class, OrderEntityMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class})
public interface MedicationBatchEntityMapper {

    MedicationBatchEntityMapper INSTANCE = Mappers.getMapper( MedicationBatchEntityMapper.class );

    @Mapping(target = "medicine", ignore = true)
    MedicationBatchEntityDTO toDTO(MedicationBatchEntity medicationBatchEntity);

    @Mapping(target = "medicine", ignore = true)
    MedicationBatchEntity toEntity(MedicationBatchEntityDTO medicationBatchEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    void updateMedicationBatchFromDto(MedicationBatchEntityDTO dto, @MappingTarget MedicationBatchEntity entity);
}
