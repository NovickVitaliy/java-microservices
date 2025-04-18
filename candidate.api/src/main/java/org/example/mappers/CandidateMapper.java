package org.example.mappers;

import org.example.dtos.CandidateCreateRequest;
import org.example.dtos.CandidateDto;
import org.example.dtos.CandidateUpdateRequest;
import org.example.models.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CandidateMapper {
    @Mapping(target = "id", ignore = true)
    public abstract Candidate toCandidate(CandidateCreateRequest request);

    @Mapping(target = "id", ignore = true)
    public abstract void updateCandidateFromRequest(CandidateUpdateRequest request, @MappingTarget Candidate candidate);

    public abstract CandidateDto toCandidateDto(Candidate candidate);
}