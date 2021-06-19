package com.sber.fortir.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("subject_science")
public class SubjectScience {
    @Id
    private Long id;
    private String name;
    private Long branchId;
}
