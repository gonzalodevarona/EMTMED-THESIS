package com.emt.med.consumable;

import com.emt.med.supply.SupplyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumableEntityRepository extends SupplyRepository<ConsumableEntity> {
}
