package com.emt.med.baseBatch;

import com.emt.med.batch.BatchEntity;
import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicationBatch.MedicationBatchEntityDTO;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {OrderEntityMapper.class, WeightUnitEntityMapper.class, CountingUnitEntityMapper.class})
public interface BaseBatchMapper {
    BaseBatchMapper INSTANCE = Mappers.getMapper( BaseBatchMapper.class );

    @SubclassMapping( source = BatchEntity.class, target = BatchEntityDTO.class )
    @SubclassMapping( source = MedicationBatchEntity.class, target = MedicationBatchEntityDTO.class )
    BaseBatchDTO toDTO(BaseBatch baseBatch);

    @InheritInverseConfiguration
    BaseBatch toEntity(BaseBatchDTO baseBatchDTO);

    @ObjectFactory
    default BaseBatch createBaseBatch(BaseBatchDTO baseBatchDTO) {
        if (baseBatchDTO instanceof MedicationBatchEntityDTO) {
            return new MedicationBatchEntity();
        } else if (baseBatchDTO instanceof BatchEntityDTO) {
            return new BatchEntity();
        }
        return null;
    }


    List<BaseBatchDTO> mapToDTO(List<BaseBatch> baseBatches);

    List<BaseBatch> map(List<BaseBatchDTO> baseBatches);
}
