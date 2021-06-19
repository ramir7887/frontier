package com.sber.fortir.controller;

import com.sber.fortir.dto.SubjectScienceDTO;
import com.sber.fortir.service.SubjectScienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectScienceController {

    private final SubjectScienceService subjectScienceService;

    public SubjectScienceController(SubjectScienceService subjectScienceService) {
        this.subjectScienceService = subjectScienceService;
    }

    @GetMapping
    public ResponseEntity<List<SubjectScienceDTO>> findAll(){
        return ResponseEntity.ok(subjectScienceService.findAll());
    }

    @PostMapping("/branch")
    public ResponseEntity<List<SubjectScienceDTO>> findBySubjectId(@RequestBody List<Long> ids){
        return ResponseEntity.ok(subjectScienceService.findUniqSubjectById(ids));
    }

    @PostMapping("/branch/create")
    public ResponseEntity<SubjectScienceDTO> save(@RequestBody SubjectScienceDTO subjectScienceDTO){
        return ResponseEntity.ok(subjectScienceService.save(subjectScienceDTO));
    }
}
