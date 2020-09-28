package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DBDatabase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DBDatabase entity.
 */
@Repository
public interface DBDatabaseRepository extends JpaRepository<DBDatabase, Long> {

    @Query(value = "select distinct dBDatabase from DBDatabase dBDatabase left join fetch dBDatabase.concerns",
        countQuery = "select count(distinct dBDatabase) from DBDatabase dBDatabase")
    Page<DBDatabase> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct dBDatabase from DBDatabase dBDatabase left join fetch dBDatabase.concerns")
    List<DBDatabase> findAllWithEagerRelationships();

    @Query("select dBDatabase from DBDatabase dBDatabase left join fetch dBDatabase.concerns where dBDatabase.id =:id")
    Optional<DBDatabase> findOneWithEagerRelationships(@Param("id") Long id);
}
