package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.dto.SensorReadingDto;
import com.aldisued.iot.monitoring.dto.SensorReadingResponseDto;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.entity.SensorType;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SensorReadingService {

    private final SensorReadingRepository sensorReadingRepository;
    private final AlertService alertService;
    private final SensorRepository sensorRepository;

    @Transactional
    public SensorReadingResponseDto saveSensorReading(SensorReadingDto sensorReadingDto) {
        UUID sensorId = sensorReadingDto.sensorId();
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found with id: " + sensorId));

        SensorType sensorType = sensor.getType();
        if (sensorType.isRuleViolated(sensorReadingDto.value())) {
            AlertDto alertDto = new AlertDto(sensorId,
                    sensorType.getMessagePrefix() + sensorReadingDto.value(),
                    sensorReadingDto.timestamp());
            alertService.saveAlert(alertDto);
        }

        SensorReading savedReading = sensorReadingRepository.save(new SensorReading(
                sensorReadingDto.value(),
                sensorReadingDto.timestamp(),
                sensor
        ));

        return new SensorReadingResponseDto(
                savedReading.getId(),
                savedReading.getValue(),
                savedReading.getTimestamp(),
                savedReading.getSensor().getId()
        );
    }

}
