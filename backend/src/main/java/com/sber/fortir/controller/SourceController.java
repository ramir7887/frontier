package com.sber.fortir.controller;

import com.sber.fortir.domain.Source;
import com.sber.fortir.service.SourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/source")
public class SourceController {

    private final SourceService sourceService;

    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @GetMapping
    public ResponseEntity<List<Source>> findAll(){
        return ResponseEntity.ok(sourceService.findAll());
    }


}
