package com.emt.med.medicine;

import com.emt.med.inventoryOrder.InventoryOrderEntityDTO;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicineEntityService {
    MedicineEntity getMedicineEntityById(Long medicineEntityId);
    MedicineEntityDTO getMedicineEntityDTOById(Long medicineEntityId);

    List<MedicineEntityDTO> getAllMedicines();

    List<MedicineEntityDTO> getAllMedicinesInStock();

    List<MedicineEntityDTO> getAllMedicinesNoBatches();

    public MedicineEntityDTO recalculateQuantity(Long medicineEntityId);

    MedicineEntityDTO addMedicine(MedicineEntityDTO medicineEntityDTO);
    InventoryOrderEntityDTO processNewMedicationBatches(MedicineEntity medicine);

    MedicineEntity saveMedicineEntity(MedicineEntity medicineEntity);


    MedicineEntityDTO removeCountingUnitFromMedicine(Long medicineEntityId);

    // Medication Batches
    MedicineEntity addMedicationBatchesToMedicine(List<MedicationBatchEntity> medicationBatchEntities, MedicineEntity medicine);

    MedicineEntityDTO removeMedicationBatchFromMedicine(Long medicineId, Long medicationBatchId);

    MedicineEntityDTO updateMedicine(MedicineEntityDTO medicineEntityDTO);

    void deleteMedicine(Long id);
}
