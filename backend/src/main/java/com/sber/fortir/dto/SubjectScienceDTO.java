package com.sber.fortir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectScienceDTO {
    private Long id;
    private Long branchId;
    private String name;
    private String isFrontir;
    private String isMckinsey;
    private String isGartner;
    private String investors;
    private String yearStat;
    private String marketSize;
}
