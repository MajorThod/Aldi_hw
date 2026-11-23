package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.entity.SensorType;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final SensorReadingRepository sensorReadingRepository;

    @Transactional(readOnly = true)
    public List<Double> getMeasurementValuesBySensorType(SensorType sensorType, Instant from, Instant to) {
        return sensorReadingRepository.findValuesBySensorTypeAndTimestampBetween(sensorType, from, to);
    }

    @Transactional(readOnly = true)
    public Optional<Double> getAverageTemperature(Instant from, Instant to) {
        return sensorReadingRepository.findAverageTemperatureBetween(from, to);
    }

}
