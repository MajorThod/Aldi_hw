package com.aldisued.iot.monitoring.repository;

import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SensorReadingRepository extends JpaRepository<SensorReading, String> {

    @Query("""
                 SELECT sr.value
                 FROM SensorReading sr
                 WHERE sr.sensor.type = :sensorType
                             AND sr.timestamp BETWEEN :from AND :to
                 ORDER BY sr.timestamp
            """)
    List<Double> findValuesBySensorTypeAndTimestampBetween(
            @Param("sensorType") SensorType sensorType,
            @Param("from") Instant from,
            @Param("to") Instant to
    );

    @Query("""
                SELECT AVG(sr.value)
                FROM SensorReading sr
                WHERE sr.sensor.type = com.aldisued.iot.monitoring.entity.SensorType.TEMPERATURE
                            AND sr.timestamp BETWEEN :from AND :to
            """)
    Optional<Double> findAverageTemperatureBetween(
            @Param("from") Instant from,
            @Param("to") Instant to
    );
}
