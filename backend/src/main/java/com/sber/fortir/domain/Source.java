package com.sber.fortir.domain;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("source")
public class Source {
    private Long id;
    private String name;
}
