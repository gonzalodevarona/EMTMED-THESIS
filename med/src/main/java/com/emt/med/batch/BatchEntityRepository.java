package com.emt.med.batch;

import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchEntityRepository extends JpaRepository<BatchEntity, Long> {
    List<BatchEntity> findAllByIsAvailable(Boolean available);
    List<BatchEntity> findByConsumable(ConsumableEntity consumable);
}
