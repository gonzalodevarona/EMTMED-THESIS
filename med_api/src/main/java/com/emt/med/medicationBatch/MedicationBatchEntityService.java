package com.emt.med.medicationBatch;

import com.emt.med.location.LocationDTO;
import com.emt.med.medicine.MedicineEntityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicationBatchEntityService {

    MedicationBatchEntity getMedicationBatchEntityById(Long medicationBatchEntityId);

    MedicationBatchEntityDTO getMedicationBatchEntityDTOById(Long medicationBatchEntityId);
    List<MedicationBatchEntityDTO> getAllMedicationBatches();
    List<MedicationBatchEntityDTO> getAllMedicationBatchesByIsAvailable(Boolean isAvailable);
    MedicineEntityDTO getMedicineByMedicationBatchId(Long medicationBatchEntityId);

    LocationDTO getLocationByMedicationBatchId(Long medicationBatchEntityId);

    MedicationBatchEntity saveMedicationBatch(MedicationBatchEntity medicationBatchEntity);
    MedicationBatchEntityDTO addMedicationBatch(MedicationBatchEntityDTO medicationBatchEntityDTO);

    MedicationBatchEntity decrementMedicationBatchQuantity(Long medicationBatchEntityId, Integer quantityToDecrement);

    MedicationBatchEntityDTO updateMedicationBatch(MedicationBatchEntity medicationBatchEntity);

    void deleteMedicationBatch(Long id);

    MedicationBatchEntityDTO removeLocationFromMedicationBatch(Long medicationBatchId, Long locationId);
}
