package com.aldisued.iot.monitoring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "sensors")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SensorType type;

    @OneToMany(mappedBy = "sensor")
    private List<SensorReading> sensorReadings = new ArrayList<>();

    @OneToMany(mappedBy = "sensor")
    private List<Alert> alerts = new ArrayList<>();

    public Sensor(String name, SensorType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sensor sensor = (Sensor) o;
        return Objects.equals(id, sensor.id) && Objects.equals(name, sensor.name)
               && type == sensor.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
    }
}
