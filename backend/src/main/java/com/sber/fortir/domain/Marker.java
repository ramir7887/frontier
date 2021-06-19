package com.sber.fortir.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("marker")
public class Marker {
    @Id
    private Long id;
    private String name;
}
