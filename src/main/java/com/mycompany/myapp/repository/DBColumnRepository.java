package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DBColumn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DBColumn entity.
 */
@Repository
public interface DBColumnRepository extends JpaRepository<DBColumn, Long> {

    @Query(value = "select distinct dBColumn from DBColumn dBColumn left join fetch dBColumn.concerns",
        countQuery = "select count(distinct dBColumn) from DBColumn dBColumn")
    Page<DBColumn> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct dBColumn from DBColumn dBColumn left join fetch dBColumn.concerns")
    List<DBColumn> findAllWithEagerRelationships();

    @Query("select dBColumn from DBColumn dBColumn left join fetch dBColumn.concerns where dBColumn.id =:id")
    Optional<DBColumn> findOneWithEagerRelationships(@Param("id") Long id);
}
