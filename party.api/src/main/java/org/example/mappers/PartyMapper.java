package org.example.mappers;

import org.example.dtos.PartyCreateRequest;
import org.example.dtos.PartyDto;
import org.example.dtos.PartyUpdateRequest;
import org.example.models.Party;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PartyMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidates", ignore = true)
    Party toParty(PartyCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidates", ignore = true)
    void updatePartyFromRequest(PartyUpdateRequest request, @MappingTarget Party party);
    PartyDto toPartyDto(Party party);
}