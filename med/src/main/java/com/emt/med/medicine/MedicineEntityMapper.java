package com.emt.med.medicine;

import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( uses = {OrderEntityMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class})
public interface MedicineEntityMapper {
    MedicineEntityMapper INSTANCE = Mappers.getMapper( MedicineEntityMapper.class );


    MedicineEntityDTO toDTO(MedicineEntity medicineEntity);


    MedicineEntity toEntity(MedicineEntityDTO medicineEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMedicineFromDTO(MedicineEntityDTO dto, @MappingTarget MedicineEntity entity);

    List<MedicineEntityDTO> map(List<MedicineEntity> medicines);
}
