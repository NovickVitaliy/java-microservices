package org.example.services;

import org.example.dtos.CandidateCreateRequest;
import org.example.dtos.CandidateDto;
import org.example.dtos.CandidateUpdateRequest;
import org.example.dtos.PartyDto;
import org.example.exceptions.VotingException;
import org.example.mappers.CandidateMapper;
import org.example.models.Candidate;
import org.example.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RestTemplate restTemplate;
    private final String partyServiceUrl = "http://localhost:9991/api/parties";

    @Autowired
    private CandidateMapper candidateMapper;

    public CandidateDto createCandidate(CandidateCreateRequest request) {
        try {
            this.restTemplate.getForEntity(STR."\{partyServiceUrl}/\{request.partyId()}",
                    PartyDto.class);
        } catch (RestClientException e){
            throw new VotingException("Party was not found");
        }


        Candidate candidate = candidateMapper.toCandidate(request);
        return candidateMapper.toCandidateDto(candidateRepository.save(candidate));
    }

    public CandidateDto getCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new VotingException("Candidate not found with id: " + id));
        return candidateMapper.toCandidateDto(candidate);
    }

    public List<CandidateDto> getAllCandidates() {
        return candidateRepository.findAll().stream()
                .map(candidateMapper::toCandidateDto)
                .toList();
    }

    public CandidateDto updateCandidate(Long id, CandidateUpdateRequest request) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new VotingException("Candidate not found with id: " + id));
        try {
            this.restTemplate.getForEntity(STR."\{partyServiceUrl}/\{request.partyId()}",
                    PartyDto.class);
        } catch (RestClientException e){
            throw new VotingException("Party was not found");
        }


        candidateMapper.updateCandidateFromRequest(request, candidate);
        return candidateMapper.toCandidateDto(candidateRepository.save(candidate));
    }

    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new VotingException("Candidate not found with id: " + id));
        candidateRepository.delete(candidate);
    }
}
