package com.emt.med.medicine;

import com.emt.med.supply.SupplyRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineEntityRepository extends SupplyRepository<MedicineEntity> {
}
