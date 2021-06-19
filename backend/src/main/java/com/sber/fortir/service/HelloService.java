package com.sber.fortir.service;

import com.sber.fortir.domain.Hello;
import com.sber.fortir.repository.HelloRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HelloService {

    private final HelloRepository helloRepository;

    public HelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    public Hello save(Hello hello) {
       log.trace(">>save... hello={}", hello);
       Optional<Hello> helloOpt = helloRepository.findById(hello.getId());
       if(helloOpt.isPresent()){
           log.trace("update hello entity");
           helloRepository.save(hello);
       }
       log.trace("create new hello entity");
       hello.setId(null);
       return helloRepository.save(hello);
    }

    public List<Hello> findAll(){
        log.trace(">>findAll");
        return helloRepository.findAll();
    }
}
