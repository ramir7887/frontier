package com.sber.fortir.repository;

import com.sber.fortir.domain.BranchScience;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepository extends CrudRepository<BranchScience, Long> {
    List<BranchScience> findAll();

    @Query(" SELECT COUNT(*) FROM (SELECT ss.id, \n" +
            "        ss.branch_id, \n" +
            "\t\tss.name, \n" +
            "\t\t'0' mckinsey,\n" +
            "\t\tCASE WHEN \n" +
            "\t\t(SELECT COUNT(*) FROM subject_market WHERE subject_id = ss.id) > 0 THEN \n" +
            "\t\t '1' \n" +
            "\t\t ELSE \n" +
            "\t\t  '0' \n" +
            "\t\t  END frontier, \n" +
            "\t\t'0' gartner, \n" +
            "\t\t'' investors,\n" +
            "\t\t'' market_size, \n" +
            "\t\t'' year_stat \n" +
            "\t\tFROM subject_science ss \n" +
            "    WHERE ss.branch_id = :id) src\n" +
            "\tWHERE src.frontier = '1'")
    Long calcCountFrontirByBranch(@Param("id") Long id);

    @Query("SELECT COUNT(*) FROM branch_science bc \n" +
            "\tJOIN subject_science sc ON sc.branch_id = bc.id\n" +
            "\tAND bc.id = :id")
    Long calcCountSubjectByBranch(@Param("id") Long id);
}
