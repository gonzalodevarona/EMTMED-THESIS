package com.emt.med.consumable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumableEntityRepository extends JpaRepository<ConsumableEntity, Long> {
}
