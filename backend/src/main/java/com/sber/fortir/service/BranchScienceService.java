package com.sber.fortir.service;

import com.sber.fortir.domain.BranchScience;
import com.sber.fortir.dto.BranchScienceDTO;
import com.sber.fortir.repository.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchScienceService {

    private final BranchRepository branchRepository;

    public BranchScienceService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<BranchScienceDTO> findAll(){
        List<BranchScience> branchSciences = branchRepository.findAll();
        List<BranchScienceDTO> branchScienceDTOS = new ArrayList<>();
        for(BranchScience branchScience : branchSciences){
            BranchScienceDTO branchScienceDTO = new BranchScienceDTO();
            branchScienceDTO.setBranchScience(branchScience);
            Long countFrontir = branchRepository.calcCountFrontirByBranch(branchScience.getId());
            branchScienceDTO.setFrontirCount(countFrontir);
            Long countSubject = branchRepository.calcCountSubjectByBranch(branchScience.getId());
            branchScienceDTO.setSubjectCount(countSubject);
            branchScienceDTOS.add(branchScienceDTO);
        }
        return branchScienceDTOS;
    }
}
