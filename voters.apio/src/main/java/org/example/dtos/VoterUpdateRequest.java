package org.example.dtos;

import jakarta.validation.constraints.NotBlank;

public record VoterUpdateRequest(
        @NotBlank(message = "Name cannot be blank") String name,
        @NotBlank(message = "Email cannot be blank") String email
) {}
