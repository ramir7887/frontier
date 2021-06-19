package com.sber.fortir.service;

import com.sber.fortir.domain.Source;
import com.sber.fortir.repository.SourceRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SourceService {
    private final SourceRepository sourceRepository;

    public SourceService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    public List<Source> findAll(){
        return sourceRepository.findAll();
    }
}
