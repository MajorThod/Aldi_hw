package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.SensorDto;
import com.aldisued.iot.monitoring.dto.SensorResponseDto;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.exception.SensorValidationException;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;

    @Transactional
    public SensorResponseDto saveSensor(SensorDto sensor) {
        if (sensorRepository.existsByName(sensor.name())) {
            throw new SensorValidationException("Sensor name already exists");
        }

        Sensor savedSensor = sensorRepository.save(
                new Sensor(sensor.name(), sensor.type())
        );

        return new SensorResponseDto(
                savedSensor.getId(),
                savedSensor.getName(),
                savedSensor.getType()
        );
    }
}
