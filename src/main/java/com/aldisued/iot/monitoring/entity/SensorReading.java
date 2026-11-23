package com.aldisued.iot.monitoring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Table(name = "sensor_readings")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class SensorReading {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private Instant timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    public SensorReading(
            Double value,
            Instant timestamp,
            Sensor sensor
    ) {
        this.value = value;
        this.timestamp = timestamp;
        this.sensor = sensor;
    }
}
