package com.sber.fortir.repository;

import com.sber.fortir.converter.SubjectScienceDTOMapper;
import com.sber.fortir.domain.SubjectScience;
import com.sber.fortir.dto.SubjectScienceDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectScienceRepository extends CrudRepository<SubjectScience, Long> {

    @Query(value = "SELECT ss.id, " +
            "ss.branch_id, " +
            "ss.name, " +
            "sm.mckinsey," +
            "sm.frontier, " +
            "sm.gartner, " +
            "sm.investors, " +
            "sm.market_size, " +
            "sm.year_stat " +
            " FROM subject_science ss \n" +
            " LEFT JOIN subject_market sm ON ss.id = sm.subject_id", rowMapperClass = SubjectScienceDTOMapper.class)
    List<SubjectScienceDTO> findSubjScienceAll();

    @Query(value = " SELECT ss.id, " +
            "ss.branch_id, " +
            "ss.name, " +
            "sm.mckinsey," +
            "sm.frontier, " +
            "sm.gartner, " +
            "sm.investors," +
            " sm.market_size, " +
            "sm.year_stat " +
            " FROM subject_science ss \n" +
            "     LEFT JOIN subject_market sm ON ss.id = sm.subject_id \n" +
            "            WHERE ss.branch_id = :id", rowMapperClass = SubjectScienceDTOMapper.class)
    List<SubjectScienceDTO> findBySubjectId(@Param("id") Long id);

    @Query(value = "SELECT ss.id, \n" +
            "        ss.branch_id, \n" +
            "\t\tss.name, \n" +
            "\t\tCASE WHEN \n" +
            "\t\t(SELECT COUNT(*) FROM subject_market WHERE mckinsey = '1' AND  subject_id = ss.id) > 0 THEN \n" +
            "\t\t '1' \n" +
            "\t\t ELSE \n" +
            "\t\t  '0' \n" +
            "\t\t  END mckinsey,\n" +
            "\t\tCASE WHEN \n" +
            "\t\t(SELECT COUNT(*) FROM subject_market WHERE subject_id = ss.id) > 0 THEN \n" +
            "\t\t '1' \n" +
            "\t\t ELSE \n" +
            "\t\t  '0' \n" +
            "\t\t  END frontier, \n" +
            "\t\tCASE WHEN \n" +
            "\t\t(SELECT COUNT(*) FROM subject_market WHERE gartner = '1' AND  subject_id = ss.id) > 0 THEN \n" +
            "\t\t '1' \n" +
            "\t\t ELSE \n" +
            "\t\t  '0' \n" +
            "\t\t  END gartner, \n" +
            "\t\t'' investors,\n" +
            "\t\t'' market_size, \n" +
            "\t\t'' year_stat \n" +
            "\t\tFROM subject_science ss \n" +
            "    WHERE ss.branch_id = :id", rowMapperClass = SubjectScienceDTOMapper.class)
    List<SubjectScienceDTO> findUniqSubjectById(@Param("id") Long id);
}
