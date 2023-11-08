package com.emt.med.medicine;

import com.emt.med.order.OrderEntityMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( uses = {OrderEntityMapper.class, WeightUnitEntityMapper.class})
public interface MedicineEntityMapper {
    MedicineEntityMapper INSTANCE = Mappers.getMapper( MedicineEntityMapper.class );
    MedicineEntityDTO toDTO(MedicineEntity medicineEntity);
    MedicineEntity toEntity(MedicineEntityDTO medicineEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMedicineFromDTO(MedicineEntityDTO dto, @MappingTarget MedicineEntity entity);
}
