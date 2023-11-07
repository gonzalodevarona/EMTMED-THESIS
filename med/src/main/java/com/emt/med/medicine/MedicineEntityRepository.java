package com.emt.med.medicine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineEntityRepository extends JpaRepository<MedicineEntity, Long> {
}
