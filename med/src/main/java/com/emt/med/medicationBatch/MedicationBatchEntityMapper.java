package com.emt.med.medicationBatch;

import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.medicine.MedicineEntityMapper;
import com.emt.med.order.OrderEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {MedicineEntityMapper.class, OrderEntityMapper.class, CountingUnitEntityMapper.class})
public interface MedicationBatchEntityMapper {

    MedicationBatchEntityMapper INSTANCE = Mappers.getMapper( MedicationBatchEntityMapper.class );

    @Mapping(target = "medicine", ignore = true)
    @Mapping(target = "inventoryOrders", ignore = true)
    @Mapping(target = "location", ignore = true)
    MedicationBatchEntityDTO toDTO(MedicationBatchEntity medicationBatchEntity);

    @Mapping(target = "medicine", ignore = true)
    @Mapping(target = "inventoryOrders", ignore = true)
    @Mapping(target = "location", ignore = true)
    MedicationBatchEntity toEntity(MedicationBatchEntityDTO medicationBatchEntityDTO);

    @Mapping(target = "inventoryOrders", ignore = true)
    @Mapping(target = "location", ignore = true)
    List<MedicationBatchEntityDTO> mapToDTO(List<MedicationBatchEntity> medicationBatches);

    @Mapping(target = "medicine", ignore = true)
    @Mapping(target = "location", ignore = true)
    List<MedicationBatchEntity> map(List<MedicationBatchEntityDTO> medicationBatches);
}
