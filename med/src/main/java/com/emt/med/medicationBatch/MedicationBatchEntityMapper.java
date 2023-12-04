package com.emt.med.medicationBatch;

import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.location.LocationMapper;
import com.emt.med.medicine.MedicineEntityMapper;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.supply.Supply;
import com.emt.med.supply.SupplyDTO;
import com.emt.med.supply.SupplyMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {MedicineEntityMapper.class, OrderEntityMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class, LocationMapper.class})
public interface MedicationBatchEntityMapper {

    MedicationBatchEntityMapper INSTANCE = Mappers.getMapper( MedicationBatchEntityMapper.class );

    @Mapping(target = "medicine", ignore = true)
    MedicationBatchEntityDTO toDTO(MedicationBatchEntity medicationBatchEntity);

    @Mapping(target = "medicine", ignore = true)
    MedicationBatchEntity toEntity(MedicationBatchEntityDTO medicationBatchEntityDTO);

    List<MedicationBatchEntityDTO> mapToDTO(List<MedicationBatchEntity> medicationBatches);

    List<MedicationBatchEntity> map(List<MedicationBatchEntityDTO> medicationBatches);
}
