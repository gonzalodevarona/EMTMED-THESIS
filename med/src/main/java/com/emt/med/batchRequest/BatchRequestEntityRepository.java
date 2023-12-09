package com.emt.med.batchRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRequestEntityRepository extends JpaRepository<BatchRequestEntity, Long> {
}
