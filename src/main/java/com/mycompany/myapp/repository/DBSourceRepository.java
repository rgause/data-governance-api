package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DBSource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DBSource entity.
 */
@Repository
public interface DBSourceRepository extends JpaRepository<DBSource, Long> {

    @Query(value = "select distinct dBSource from DBSource dBSource left join fetch dBSource.concerns",
        countQuery = "select count(distinct dBSource) from DBSource dBSource")
    Page<DBSource> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct dBSource from DBSource dBSource left join fetch dBSource.concerns")
    List<DBSource> findAllWithEagerRelationships();

    @Query("select dBSource from DBSource dBSource left join fetch dBSource.concerns where dBSource.id =:id")
    Optional<DBSource> findOneWithEagerRelationships(@Param("id") Long id);
}
