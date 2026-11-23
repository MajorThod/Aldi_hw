package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.dto.AlertResponseDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.exception.KafkaUnavailableException;
import com.aldisued.iot.monitoring.exception.SensorValidationException;
import com.aldisued.iot.monitoring.messaging.AlertKafkaProducer;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final SensorRepository sensorRepository;
    private final AlertKafkaProducer alertKafkaProducer;

    @Transactional
    public AlertResponseDto saveAlert(AlertDto alertDto) {
        UUID sensorId = alertDto.sensorId();
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new SensorValidationException("Could not find sensor id: " + sensorId));

        Alert savedAlert = alertRepository.save(new Alert(alertDto.message(), alertDto.timestamp(), sensor));

        alertKafkaProducer.sendAlert(alertDto);

        return new AlertResponseDto(
                savedAlert.getId(),
                savedAlert.getMessage(),
                savedAlert.getTimestamp(),
                savedAlert.getSensor().getId()
        );
    }

    public AlertDto findLastAlertBySensorId(UUID sensorId) {
        Alert alert = alertRepository.findFirstBySensorIdOrderByTimestampDesc(sensorId).orElse(null);
        if (alert == null) {
            return null;
        }
        return new AlertDto(sensorId, alert.getMessage(), alert.getTimestamp());
    }
}
