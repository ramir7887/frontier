package com.sber.fortir.repository;

import com.sber.fortir.domain.Marker;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MarkerRepository extends CrudRepository<Marker, Long> {
    List<Marker> findAll();
}
