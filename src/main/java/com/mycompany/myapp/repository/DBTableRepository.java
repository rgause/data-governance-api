package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DBTable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DBTable entity.
 */
@Repository
public interface DBTableRepository extends JpaRepository<DBTable, Long> {

    @Query(value = "select distinct dBTable from DBTable dBTable left join fetch dBTable.concerns",
        countQuery = "select count(distinct dBTable) from DBTable dBTable")
    Page<DBTable> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct dBTable from DBTable dBTable left join fetch dBTable.concerns")
    List<DBTable> findAllWithEagerRelationships();

    @Query("select dBTable from DBTable dBTable left join fetch dBTable.concerns where dBTable.id =:id")
    Optional<DBTable> findOneWithEagerRelationships(@Param("id") Long id);
}
