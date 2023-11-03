package com.emt.med.weightUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightUnitEntityRepository extends JpaRepository<WeightUnitEntity, Long> {
}
