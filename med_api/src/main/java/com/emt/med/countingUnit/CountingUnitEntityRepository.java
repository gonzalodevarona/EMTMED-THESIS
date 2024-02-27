package com.emt.med.countingUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountingUnitEntityRepository extends JpaRepository<CountingUnitEntity, Long> {
}
