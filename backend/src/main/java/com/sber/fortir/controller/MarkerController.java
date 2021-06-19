package com.sber.fortir.controller;

import com.sber.fortir.domain.Marker;
import com.sber.fortir.service.MarkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/marker")
public class MarkerController {

    private final MarkerService markerService;

    public MarkerController(MarkerService markerService) {
        this.markerService = markerService;
    }

    @GetMapping
    public ResponseEntity<List<Marker>> findAll(){
        return ResponseEntity.ok(markerService.findAll());
    }
}
