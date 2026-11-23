package com.aldisued.iot.monitoring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record AlertDto(
        @NotNull UUID sensorId,
        @NotBlank String message,
        @NotNull Instant timestamp
) {
}
