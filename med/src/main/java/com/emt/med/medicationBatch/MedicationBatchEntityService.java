package com.emt.med.medicationBatch;

import com.emt.med.batch.BatchEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MedicationBatchEntityService {

    MedicationBatchEntity getMedicationBatchEntityById(Long medicationBatchEntityId);

    MedicationBatchEntityDTO getMedicationBatchEntityDTOById(Long medicationBatchEntityId);
    List<MedicationBatchEntityDTO> getAllMedicationBatches();

    MedicationBatchEntity saveMedicationBatch(MedicationBatchEntity medicationBatchEntity);
    MedicationBatchEntityDTO addMedicationBatch(MedicationBatchEntityDTO medicationBatchEntityDTO);

    MedicationBatchEntityDTO updateMedicationBatch(MedicationBatchEntityDTO medicationBatchEntityDTO);

    void deleteMedicationBatch(Long id);

    MedicationBatchEntityDTO removeLocationFromMedicationBatch(Long medicationBatchId, Long locationId);
}
