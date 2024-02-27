package com.emt.med.supplyOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyOrderEntityRepository extends JpaRepository<SupplyOrderEntity, Long> {
}
