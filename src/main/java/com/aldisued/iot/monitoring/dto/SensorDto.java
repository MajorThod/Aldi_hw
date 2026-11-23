package com.aldisued.iot.monitoring.dto;

import com.aldisued.iot.monitoring.entity.SensorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SensorDto(
        @NotBlank(message = "Sensor name must be provided") String name,
        @NotNull SensorType type
) {
}
