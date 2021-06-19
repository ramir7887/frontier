package com.sber.fortir.repository;

import com.sber.fortir.domain.SubjectMarket;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectMarketRepository extends CrudRepository<SubjectMarket, Long> {
    List<SubjectMarket> findAll();

    @Query("SELECT * FROM subject_market WHERE subject_id in (:ids)")
    List<SubjectMarket> findBySubjectIds(@Param("ids") List<Long> ids);
}
