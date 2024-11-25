package edu.miu.cse.controllertestdemo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
        String firstName,
        String lastName,
        @NotNull
        String username) {
}
