package com.sber.fortir.dto;

import com.sber.fortir.domain.BranchScience;
import lombok.Data;

@Data
public class BranchScienceDTO {
    private BranchScience branchScience;
    private Long subjectCount;
    private Long frontirCount;
}
