package org.example.services;

import org.example.dtos.*;
import org.example.exceptions.VotingException;
import org.example.mappers.PartyMapper;
import org.example.models.Party;
import org.example.repositories.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyMapper partyMapper;

    public PartyDto createParty(PartyCreateRequest request) {
        Party party = partyMapper.toParty(request);
        return partyMapper.toPartyDto(partyRepository.save(party));
    }

    public PartyDto getParty(Long id) {
        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new VotingException("Party not found with id: " + id));
        return partyMapper.toPartyDto(party);
    }

    public List<PartyDto> getAllParties() {
        return partyRepository.findAll().stream()
                .map(partyMapper::toPartyDto)
                .toList();
    }

    public PartyDto updateParty(Long id, PartyUpdateRequest request) {
        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new VotingException("Party not found with id: " + id));
        partyMapper.updatePartyFromRequest(request, party);
        return partyMapper.toPartyDto(partyRepository.save(party));
    }

    public void deleteParty(Long id) {
        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new VotingException("Party not found with id: " + id));
        partyRepository.delete(party);
    }
}
