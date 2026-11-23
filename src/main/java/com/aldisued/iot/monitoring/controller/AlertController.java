package com.aldisued.iot.monitoring.controller;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.dto.AlertResponseDto;
import com.aldisued.iot.monitoring.exception.SensorValidationException;
import com.aldisued.iot.monitoring.service.AlertService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/latest")
    public ResponseEntity<AlertDto> findLastAlertBySensorId(@RequestParam UUID sensorId) {
        AlertDto result = alertService.findLastAlertBySensorId(sensorId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<Object> saveAlert(@RequestBody @Valid AlertDto alertDto) {
        try {
            AlertResponseDto dto = alertService.saveAlert(alertDto);
            return ResponseEntity.ok().body(dto);
        } catch (SensorValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
