package com.sber.fortir.service;

import com.sber.fortir.domain.SubjectMarket;
import com.sber.fortir.repository.SubjectMarketRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectMarketService {

    private final SubjectMarketRepository subjectMarketRepository;

    public SubjectMarketService(SubjectMarketRepository subjectMarketRepository) {
        this.subjectMarketRepository = subjectMarketRepository;
    }

    public List<SubjectMarket> findAll(){
        return subjectMarketRepository.findAll();
    }

    public List<SubjectMarket> findBySubjectIds(List<Long> ids){
        return subjectMarketRepository.findBySubjectIds(ids);
    }

    public Optional<SubjectMarket> findById(Long id){
        return subjectMarketRepository.findById(id);
    }

    public SubjectMarket save(SubjectMarket subjectMarket) {
       Optional<SubjectMarket> subjectMarketOpt =
               subjectMarketRepository.findById(subjectMarket.getId() == null ? -1 : subjectMarket.getId());
        if(subjectMarketOpt.isPresent()){
          return  subjectMarketRepository.save(subjectMarket);
        }
        subjectMarket.setId(null);
        return subjectMarketRepository.save(subjectMarket);
    }
}
