package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dtos.CandidateCreateRequest;
import org.example.dtos.CandidateDto;
import org.example.dtos.CandidateUpdateRequest;
import org.example.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public ResponseEntity<CandidateDto> createCandidate(@Valid @RequestBody CandidateCreateRequest request) {
        CandidateDto candidateDto = candidateService.createCandidate(request);
        return ResponseEntity.ok(candidateDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getCandidate(@PathVariable("id") Long id) {
        CandidateDto candidateDto = candidateService.getCandidate(id);
        return ResponseEntity.ok(candidateDto);
    }

    @GetMapping
    public ResponseEntity<List<CandidateDto>> getAllCandidates() {
        List<CandidateDto> candidates = candidateService.getAllCandidates();
        return ResponseEntity.ok(candidates);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDto> updateCandidate(@PathVariable("id") Long id,
                                                        @Valid @RequestBody CandidateUpdateRequest request) {
        CandidateDto candidateDto = candidateService.updateCandidate(id, request);
        return ResponseEntity.ok(candidateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable("id") Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }
}
