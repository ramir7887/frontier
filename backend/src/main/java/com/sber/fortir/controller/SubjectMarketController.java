package com.sber.fortir.controller;

import com.sber.fortir.service.SubjectMarketService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subject")
public class SubjectMarketController {

    private final SubjectMarketService subjectMarketService;

    public SubjectMarketController(SubjectMarketService subjectMarketService) {
        this.subjectMarketService = subjectMarketService;
    }

}
