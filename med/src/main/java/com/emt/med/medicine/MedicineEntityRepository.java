package com.emt.med.medicine;

import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.supply.SupplyPurpose;
import com.emt.med.supply.SupplyRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineEntityRepository extends SupplyRepository<MedicineEntity> {
}
