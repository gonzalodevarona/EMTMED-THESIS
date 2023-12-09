package com.emt.med.medicationBatchRequest;

import com.emt.med.weightUnit.WeightUnitEntity;
import com.emt.med.weightUnit.WeightUnitEntityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicationBatchRequestEntityService {
    MedicationBatchRequestEntity getMedicationBatchRequestById(Long medicationBatchRequestEntityId);

    MedicationBatchRequestEntityDTO getMedicationBatchRequestDTOtById(Long medicationBatchRequestEntityId);
    List<MedicationBatchRequestEntityDTO> getAllMedicationBatchRequests();
    MedicationBatchRequestEntityDTO addMedicationBatchRequest(MedicationBatchRequestEntityDTO medicationBatchRequestEntityDTO);

    MedicationBatchRequestEntity saveMedicationBatchRequest(MedicationBatchRequestEntity medicationBatchRequestEntity);


}
