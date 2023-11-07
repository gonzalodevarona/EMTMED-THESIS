package com.emt.med.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface LocationRepository extends JpaRepository<Location, Long> {
}
