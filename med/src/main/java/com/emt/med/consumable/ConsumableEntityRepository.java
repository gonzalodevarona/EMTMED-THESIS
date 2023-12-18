package com.emt.med.consumable;

import com.emt.med.supply.SupplyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumableEntityRepository extends SupplyRepository<ConsumableEntity> {
    List<ConsumableEntity> findByQuantityGreaterThanEqualOrderByQuantityDesc(Long quantity);

}
