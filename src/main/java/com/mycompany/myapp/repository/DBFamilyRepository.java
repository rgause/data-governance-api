package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DBFamily;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DBFamily entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DBFamilyRepository extends JpaRepository<DBFamily, Long> {
}
