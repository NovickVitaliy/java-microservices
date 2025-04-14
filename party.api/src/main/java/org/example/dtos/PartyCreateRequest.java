package org.example.dtos;

import jakarta.validation.constraints.NotBlank;

public record PartyCreateRequest(
        @NotBlank(message = "Name cannot be blank") String name
) {}
