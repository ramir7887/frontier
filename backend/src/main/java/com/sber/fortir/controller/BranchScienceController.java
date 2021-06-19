package com.sber.fortir.controller;

import com.sber.fortir.dto.BranchScienceDTO;
import com.sber.fortir.service.BranchScienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchScienceController {
    
    private final BranchScienceService branchScienceService;

    public BranchScienceController(BranchScienceService branchScienceService) {
        this.branchScienceService = branchScienceService;
    }
    
    @GetMapping
    public ResponseEntity<List<BranchScienceDTO>> findAll(){
        return ResponseEntity.ok(branchScienceService.findAll());
    }
}
