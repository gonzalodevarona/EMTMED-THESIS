package com.emt.med.medicine;

import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.supply.Supply;
import com.emt.med.supply.SupplyPurpose;
import com.emt.med.weightUnit.WeightUnitEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public interface MedicineEntityService {
    MedicineEntity getMedicineEntityById(Long medicineEntityId);
    MedicineEntityDTO getMedicineEntityDTOById(Long medicineEntityId);

    List<MedicineEntityDTO> getAllMedicines();

    List<MedicineEntityDTO> getMedicinesByPurpose(SupplyPurpose supplyPurpose);

    List<MedicineEntityDTO> getAllMedicinesNoOrdersNoBatches(SupplyPurpose supplyPurpose);

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
