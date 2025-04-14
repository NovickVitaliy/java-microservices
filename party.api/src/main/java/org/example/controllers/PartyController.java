package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dtos.PartyCreateRequest;
import org.example.dtos.PartyDto;
import org.example.dtos.PartyUpdateRequest;
import org.example.services.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parties")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @PostMapping
    public ResponseEntity<PartyDto> createParty(@Valid @RequestBody PartyCreateRequest request) {
        PartyDto partyDto = partyService.createParty(request);
        return ResponseEntity.ok(partyDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartyDto> getParty(@PathVariable Long id) {
        PartyDto partyDto = partyService.getParty(id);
        return ResponseEntity.ok(partyDto);
    }

    @GetMapping
    public ResponseEntity<List<PartyDto>> getAllParties() {
        List<PartyDto> parties = partyService.getAllParties();
        return ResponseEntity.ok(parties);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartyDto> updateParty(@PathVariable Long id, @Valid @RequestBody PartyUpdateRequest request) {
        PartyDto partyDto = partyService.updateParty(id, request);
        return ResponseEntity.ok(partyDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long id) {
        partyService.deleteParty(id);
        return ResponseEntity.noContent().build();
    }
}
