package com.sber.fortir.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("subject_market")
public class SubjectMarket {
    @Id
    private Long id;
    private Long subjectId;
    private String yearStat;
    private String marketSize;
    private String investors;
    private String gartner;
    private String mckinsey;
    private String frontier;
}
