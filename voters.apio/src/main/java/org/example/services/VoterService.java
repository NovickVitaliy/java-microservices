package org.example.services;

import org.example.dtos.VoterCreateRequest;
import org.example.dtos.VoterDto;
import org.example.dtos.VoterUpdateRequest;
import org.example.exceptions.VotingException;
import org.example.mappers.VoterMapper;
import org.example.models.Voter;
import org.example.repositories.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VoterService {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private VoterMapper voterMapper;

    public VoterDto createVoter(VoterCreateRequest request) {
        Voter voter = voterMapper.toVoter(request);
        return voterMapper.toVoterDto(voterRepository.save(voter));
    }

    public VoterDto getVoter(Long id) {
        Voter voter = voterRepository.findById(id)
                .orElseThrow(() -> new VotingException("Voter not found with id: " + id));
        return voterMapper.toVoterDto(voter);
    }

    public List<VoterDto> getAllVoters() {
        return voterRepository.findAll().stream()
                .map(voterMapper::toVoterDto)
                .toList();
    }

    public VoterDto updateVoter(Long id, VoterUpdateRequest request) {
        Voter voter = voterRepository.findById(id)
                .orElseThrow(() -> new VotingException("Voter not found with id: " + id));
        voterMapper.updateVoterFromRequest(request, voter);
        return voterMapper.toVoterDto(voterRepository.save(voter));
    }

    public void deleteVoter(Long id) {
        Voter voter = voterRepository.findById(id)
                .orElseThrow(() -> new VotingException("Voter not found with id: " + id));
        voterRepository.delete(voter);
    }
}