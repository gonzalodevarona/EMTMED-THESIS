package com.emt.med.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
}
