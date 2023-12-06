package com.emt.med.supply;

import com.emt.med.medicine.MedicineEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface SupplyRepository<T extends Supply> extends JpaRepository<T, Long>{
    List<T> findByPurpose(SupplyPurpose purpose, Sort sort);
}
