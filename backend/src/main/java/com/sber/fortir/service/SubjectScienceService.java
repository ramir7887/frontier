package com.sber.fortir.service;

import com.sber.fortir.domain.SubjectMarket;
import com.sber.fortir.domain.SubjectScience;
import com.sber.fortir.dto.SubjectScienceDTO;
import com.sber.fortir.repository.SubjectScienceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class SubjectScienceService {

    private final SubjectScienceRepository subjectScienceRepository;
    private final SubjectMarketService subjectMarketService;

    public SubjectScienceService(SubjectScienceRepository subjectScienceRepository,
                                 SubjectMarketService subjectMarketService) {
        this.subjectScienceRepository = subjectScienceRepository;
        this.subjectMarketService = subjectMarketService;
    }

    public List<SubjectScienceDTO> findAll() {
        return subjectScienceRepository.findSubjScienceAll();
    }


    public List<SubjectScienceDTO> findBySubjectId(Long id) {
        return subjectScienceRepository.findBySubjectId(id);
    }

    public List<SubjectScienceDTO> findUniqSubjectById(Long id) {
        return subjectScienceRepository.findUniqSubjectById(id);
    }

    public List<SubjectScienceDTO> findBySubjectId(List<Long> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<SubjectScienceDTO> subjects = new ArrayList<>();
        for (Long id : ids) {
            var sub = findBySubjectId(id);
            subjects.addAll(sub);
        }

        return subjects;
    }

    public  List<SubjectScienceDTO> findUniqSubjectById(List<Long> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<SubjectScienceDTO> subjects = new ArrayList<>();
        for (Long id : ids) {
            var sub = findUniqSubjectById(id);
            subjects.addAll(sub);
        }

        return subjects;
    }

    @Transactional
    public SubjectScienceDTO save(SubjectScienceDTO subjectScienceDTO) {
        Optional<SubjectScience> subjectScienceOpt = subjectScienceRepository.findById(subjectScienceDTO.getId() == null ? -1 :
                subjectScienceDTO.getId() );
        SubjectScience newSubjScience = null;
        var subjectScience = new SubjectScience();
        subjectScience.setName(subjectScienceDTO.getName());
        subjectScience.setBranchId(subjectScienceDTO.getBranchId());
        if (subjectScienceOpt.isEmpty()) {
            subjectScience.setId(null);
            newSubjScience = subjectScienceRepository.save(subjectScience);
        } else {
            subjectScience.setId(subjectScienceDTO.getId());
            newSubjScience = subjectScienceRepository.save(subjectScience);
        }
        var subjectMarket = new SubjectMarket();
        subjectMarket.setSubjectId(newSubjScience.getId());
        subjectMarket.setGartner(
                String.valueOf(subjectScienceDTO.getIsGartner() != null ? subjectScienceDTO.getIsGartner() : "0")
        );
        subjectMarket.setMckinsey(
                String.valueOf(subjectScienceDTO.getIsMckinsey() != null ? subjectScienceDTO.getIsMckinsey() : "0")
        );
        subjectMarket.setInvestors(
                String.valueOf(subjectScienceDTO.getInvestors() != null ? subjectScienceDTO.getInvestors() : "")
        );
        subjectMarket.setYearStat(
                String.valueOf(subjectScienceDTO.getYearStat() != null ? subjectScienceDTO.getYearStat() : "")
        );
        SubjectMarket newSubjectMarket = subjectMarketService.save(subjectMarket);

        return new SubjectScienceDTO(
                newSubjScience.getId(),
                newSubjScience.getBranchId(),
                newSubjScience.getName(),
                newSubjectMarket.getFrontier(),
                newSubjectMarket.getMckinsey(),
                newSubjectMarket.getGartner(),
                newSubjectMarket.getInvestors(),
                newSubjectMarket.getYearStat(),
                newSubjectMarket.getMarketSize()
        );
    }
}
