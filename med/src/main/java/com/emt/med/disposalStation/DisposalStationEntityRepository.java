package com.emt.med.disposalStation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisposalStationEntityRepository extends JpaRepository<DisposalStationEntity, Long> {
}
