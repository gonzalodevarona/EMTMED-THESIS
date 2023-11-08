package com.emt.med.supply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SupplyRepository<T extends Supply> extends JpaRepository<T, Long>{}
