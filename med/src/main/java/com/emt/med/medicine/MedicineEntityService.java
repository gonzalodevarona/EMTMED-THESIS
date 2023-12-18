package com.emt.med.medicine;

import com.emt.med.medicationBatch.MedicationBatchEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicineEntityService {
    MedicineEntity getMedicineEntityById(Long medicineEntityId);
    MedicineEntityDTO getMedicineEntityDTOById(Long medicineEntityId);

    List<MedicineEntityDTO> getAllMedicines();

    List<MedicineEntityDTO> getAllMedicinesNoBatches();

    MedicineEntityDTO addMedicine(MedicineEntityDTO medicineEntityDTO);

    MedicineEntity saveMedicineEntity(MedicineEntity medicineEntity);


    MedicineEntityDTO removeWeightUnitFromMedicine(Long medicineEntityId);


    MedicineEntityDTO removeCountingUnitFromMedicine(Long medicineEntityId);

    // Medication Batches
    MedicineEntity addMedicationBatchesToMedicine(List<MedicationBatchEntity> medicationBatchEntities, MedicineEntity medicine);

    MedicineEntityDTO removeMedicationBatchFromMedicine(Long medicineId, Long medicationBatchId);

    MedicineEntityDTO updateMedicine(MedicineEntityDTO medicineEntityDTO);

    void deleteMedicine(Long id);
}
