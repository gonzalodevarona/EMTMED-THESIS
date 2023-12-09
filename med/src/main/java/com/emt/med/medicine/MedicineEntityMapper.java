package com.emt.med.medicine;

import com.emt.med.batch.BatchEntityMapper;
import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicationBatch.MedicationBatchEntityDTO;
import com.emt.med.medicationBatch.MedicationBatchEntityMapper;
import com.emt.med.order.OrderEntity;
import com.emt.med.order.OrderEntityDTO;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.supply.SupplyMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( uses = {SupplyMapper.class, OrderEntityMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class, MedicationBatchEntityMapper.class, LocationMapper.class})
public interface MedicineEntityMapper {
    MedicineEntityMapper INSTANCE = Mappers.getMapper( MedicineEntityMapper.class );

    @Mapping(target = "orders", ignore = true)
    MedicineEntityDTO toDTO(MedicineEntity medicineEntity);

    @Named("toDTONoOrdersNoBatches")
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "batches", ignore = true)
    MedicineEntityDTO toDTONoOrdersNoBatches(MedicineEntity medicineEntity);

    @Mapping(target = "orders", ignore = true)
    MedicineEntity toEntity(MedicineEntityDTO medicineEntityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMedicineFromDTO(MedicineEntityDTO dto, @MappingTarget MedicineEntity entity);

    List<MedicineEntityDTO> toMedicineDTOList(List<MedicineEntity> medicines);

    List<MedicineEntity> toMedicineList(List<MedicineEntityDTO> medicinesDto);
}
