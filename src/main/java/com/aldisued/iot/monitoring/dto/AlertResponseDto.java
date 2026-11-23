package com.aldisued.iot.monitoring.dto;

import java.time.Instant;
import java.util.UUID;

public record AlertResponseDto(
        Long id,
        String message,
        Instant timestamp,
        UUID sensorId
) {}