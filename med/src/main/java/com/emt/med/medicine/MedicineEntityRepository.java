package com.emt.med.medicine;

import com.emt.med.supply.SupplyRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineEntityRepository extends SupplyRepository<MedicineEntity> {
}
