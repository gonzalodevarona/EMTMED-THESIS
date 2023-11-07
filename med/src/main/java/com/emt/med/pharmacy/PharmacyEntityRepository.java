package com.emt.med.pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyEntityRepository extends JpaRepository<PharmacyEntity, Long> {
}
