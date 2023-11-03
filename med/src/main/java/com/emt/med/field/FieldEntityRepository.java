package com.emt.med.field;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldEntityRepository extends JpaRepository<FieldEntity, Long> {
}
