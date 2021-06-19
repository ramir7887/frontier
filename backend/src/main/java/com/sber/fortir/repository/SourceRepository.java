package com.sber.fortir.repository;

import com.sber.fortir.domain.Source;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface SourceRepository extends CrudRepository<Source, Long> {
    List<Source> findAll();
}
