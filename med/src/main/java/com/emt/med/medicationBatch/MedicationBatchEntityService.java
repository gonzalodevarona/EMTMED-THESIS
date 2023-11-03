package com.emt.med.medicationBatch;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MedicationBatchEntityService {

    MedicationBatchEntityDTO getMedicationBatchEntityById(Long medicationBatchEntityId);
    List<MedicationBatchEntityDTO> getAllMedicationBatches();
    MedicationBatchEntityDTO addMedicationBatch(MedicationBatchEntityDTO medicationBatchEntityDTO);

    MedicationBatchEntityDTO updateMedicationBatch(MedicationBatchEntityDTO medicationBatchEntityDTO);

    void deleteMedicationBatch(Long id);
}
