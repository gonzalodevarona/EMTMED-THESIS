package com.emt.med.medicationBatchRequest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicationBatchRequestEntityService {

    MedicationBatchRequestEntityDTO getMedicationBatchRequestDTOtById(Long medicationBatchRequestEntityId);
    List<MedicationBatchRequestEntityDTO> getAllMedicationBatchRequests();
    MedicationBatchRequestEntityDTO addMedicationBatchRequest(MedicationBatchRequestEntityDTO medicationBatchRequestEntityDTO);

    MedicationBatchRequestEntity saveMedicationBatchRequest(MedicationBatchRequestEntity medicationBatchRequestEntity);


}
