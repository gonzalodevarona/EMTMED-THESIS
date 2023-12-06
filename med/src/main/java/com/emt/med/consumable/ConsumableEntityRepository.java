package com.emt.med.consumable;

import com.emt.med.medicine.MedicineEntity;
import com.emt.med.supply.SupplyPurpose;
import com.emt.med.supply.SupplyRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumableEntityRepository extends SupplyRepository<ConsumableEntity> {

}
