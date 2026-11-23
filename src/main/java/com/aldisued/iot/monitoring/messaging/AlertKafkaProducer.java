package com.aldisued.iot.monitoring.messaging;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.exception.KafkaUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertKafkaProducer {

    @Value("${app.kafka.topics.alerts}")
    private String alertsTopic;

    private final KafkaTemplate<String, AlertDto> kafkaTemplate;

    public void sendAlert(AlertDto alertDto) {
        try {
            kafkaTemplate.send(alertsTopic, alertDto).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new KafkaUnavailableException("Kafka send interrupted", e);
        } catch (Exception e) {
            throw new KafkaUnavailableException("Kafka send failed", e);
        }
    }
}
