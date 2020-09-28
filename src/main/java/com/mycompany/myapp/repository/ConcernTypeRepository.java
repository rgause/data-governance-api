package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ConcernType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConcernType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConcernTypeRepository extends JpaRepository<ConcernType, Long> {
}
