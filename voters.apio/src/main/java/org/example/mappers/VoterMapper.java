package org.example.mappers;

import org.example.dtos.*;
import org.example.models.Voter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoterMapper {
    @Mapping(target = "id", ignore = true)
    Voter toVoter(VoterCreateRequest request);

    @Mapping(target = "id", ignore = true)
    void updateVoterFromRequest(VoterUpdateRequest request, @MappingTarget Voter voter);
    VoterDto toVoterDto(Voter voter);
}