package com.sber.fortir.service;

import com.sber.fortir.domain.Marker;
import com.sber.fortir.repository.MarkerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MarkerService {

    private final MarkerRepository markerRepository;

    public MarkerService(MarkerRepository markerRepository) {
        this.markerRepository = markerRepository;
    }

    public List<Marker> findAll() {
        return markerRepository.findAll();
    }
}
