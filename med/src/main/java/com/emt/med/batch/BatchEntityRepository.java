package com.emt.med.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchEntityRepository extends JpaRepository<BatchEntity, Long> {
}
