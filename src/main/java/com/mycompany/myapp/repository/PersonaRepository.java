package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Persona;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Persona entity.
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    @Query(value = "select distinct persona from Persona persona left join fetch persona.columns",
        countQuery = "select count(distinct persona) from Persona persona")
    Page<Persona> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct persona from Persona persona left join fetch persona.columns")
    List<Persona> findAllWithEagerRelationships();

    @Query("select persona from Persona persona left join fetch persona.columns where persona.id =:id")
    Optional<Persona> findOneWithEagerRelationships(@Param("id") Long id);
}
