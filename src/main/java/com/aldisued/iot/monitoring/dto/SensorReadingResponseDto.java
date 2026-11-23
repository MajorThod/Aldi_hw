package com.aldisued.iot.monitoring.dto;

import java.time.Instant;
import java.util.UUID;

public record SensorReadingResponseDto(
        Long id,
        Double value,
        Instant timestamp,
        UUID sensorId
) {}