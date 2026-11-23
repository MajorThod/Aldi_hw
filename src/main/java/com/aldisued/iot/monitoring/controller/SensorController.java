package com.aldisued.iot.monitoring.controller;

import com.aldisued.iot.monitoring.dto.SensorDto;
import com.aldisued.iot.monitoring.dto.SensorResponseDto;
import com.aldisued.iot.monitoring.exception.SensorValidationException;
import com.aldisued.iot.monitoring.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping
    public ResponseEntity<Object> saveSensor(@RequestBody @Valid SensorDto sensorDto) {
        try {
            SensorResponseDto dto = sensorService.saveSensor(sensorDto);
            return ResponseEntity.ok().body(dto);
        } catch (SensorValidationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}