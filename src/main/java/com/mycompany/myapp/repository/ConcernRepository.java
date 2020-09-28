package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Concern;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Concern entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConcernRepository extends JpaRepository<Concern, Long> {
}
