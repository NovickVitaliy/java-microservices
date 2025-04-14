package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dtos.*;
import org.example.services.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/voters")
public class VoterController {

    @Autowired
    private VoterService voterService;

    @PostMapping
    public ResponseEntity<VoterDto> createVoter(@Valid @RequestBody VoterCreateRequest request) {
        VoterDto voterDto = voterService.createVoter(request);
        return ResponseEntity.ok(voterDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoterDto> getVoter(@PathVariable("id") Long id) {
        VoterDto voterDto = voterService.getVoter(id);
        return ResponseEntity.ok(voterDto);
    }

    @GetMapping
    public ResponseEntity<List<VoterDto>> getAllVoters() {
        List<VoterDto> voters = voterService.getAllVoters();
        return ResponseEntity.ok(voters);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoterDto> updateVoter(@PathVariable("id") Long id,
                                                @Valid @RequestBody VoterUpdateRequest request) {
        VoterDto voterDto = voterService.updateVoter(id, request);
        return ResponseEntity.ok(voterDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoter(@PathVariable("id") Long id) {
        voterService.deleteVoter(id);
        return ResponseEntity.noContent().build();
    }
}

