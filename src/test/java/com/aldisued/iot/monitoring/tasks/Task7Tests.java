package com.aldisued.iot.monitoring.tasks;

import com.aldisued.iot.monitoring.IntegrationTestBase;
import com.aldisued.iot.monitoring.service.MeasurementService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@Sql(scripts = "/sql/task-7-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
public class Task7Tests extends IntegrationTestBase {

    @Autowired
    private MeasurementService measurementService;

    @ParameterizedTest
    @MethodSource("averageTemperatureParameters")
    public void verifyAverageTemperatureCalculation(Instant from, Instant to,
                                                    Optional<Double> expectedValue) {
        var average = measurementService.getAverageTemperature(from, to);

        Assertions.assertEquals(expectedValue, average);
    }

    public static Stream<Arguments> averageTemperatureParameters() {
        return Stream.of(
                Arguments.of(
                        Instant.parse("2020-01-01T00:00:00Z"),
                        Instant.parse("2020-12-31T23:59:59Z"),
                        Optional.of(16.5)
                ),
                Arguments.of(
                        Instant.parse("2020-01-01T00:00:00Z"),
                        Instant.parse("2020-06-23T23:59:59Z"),
                        Optional.of(18.5)
                ),
                Arguments.of(
                        Instant.parse("2025-01-01T00:00:00Z"),
                        Instant.parse("2025-12-31T23:59:59Z"),
                        Optional.empty()
                )
        );
    }

}
