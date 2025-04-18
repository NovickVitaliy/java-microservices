package org.example.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CandidateUpdateRequest(
        @NotBlank(message = "Name cannot be blank") String name,
        @NotNull(message = "Party ID cannot be null") Long partyId
) {}