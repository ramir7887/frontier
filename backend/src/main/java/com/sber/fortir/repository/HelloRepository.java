package com.sber.fortir.repository;

import com.sber.fortir.domain.Hello;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HelloRepository extends CrudRepository<Hello, Long> {
    List<Hello> findAll();
}
