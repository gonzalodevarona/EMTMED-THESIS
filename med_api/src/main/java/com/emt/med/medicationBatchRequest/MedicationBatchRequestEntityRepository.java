package com.emt.med.medicationBatchRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationBatchRequestEntityRepository extends JpaRepository<MedicationBatchRequestEntity, Long> {
}
