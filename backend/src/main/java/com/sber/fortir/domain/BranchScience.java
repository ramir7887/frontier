package com.sber.fortir.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("branch_science")
public class BranchScience {
    @Id
    private Long id;
    private String name;
}
