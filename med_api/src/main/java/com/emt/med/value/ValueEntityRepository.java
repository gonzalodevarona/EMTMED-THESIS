package com.emt.med.value;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueEntityRepository extends JpaRepository<ValueEntity,Long> {
}
