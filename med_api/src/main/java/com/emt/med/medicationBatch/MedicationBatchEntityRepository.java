package com.emt.med.medicationBatch;

import com.emt.med.medicine.MedicineEntity;
import com.emt.med.pharmacy.PharmacyCategory;
import com.emt.med.pharmacy.PharmacyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationBatchEntityRepository extends JpaRepository<MedicationBatchEntity, Long> {
    List<MedicationBatchEntity> findAllByIsAvailable(Boolean available);
    List<MedicationBatchEntity> findByMedicine(MedicineEntity medicine);
}
