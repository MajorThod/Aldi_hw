package com.aldisued.iot.monitoring.service;


import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MeasurementCalculatorService {

    private static final Double MIN_ALLOWED_DEVIATION = 0.0;
    private static final Double MAX_ALLOWED_DEVIATION = 1.0;

    public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
        if (deviation < MIN_ALLOWED_DEVIATION || deviation > MAX_ALLOWED_DEVIATION)
            throw new IllegalArgumentException(String.format("Deviation must be between [%f, %f]",
                    MIN_ALLOWED_DEVIATION, MAX_ALLOWED_DEVIATION));

        double average = values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        return values.stream()
                .filter(aDouble -> isWithinAcceptableRange(aDouble, average, deviation))
                .toList();
    }

    private boolean isWithinAcceptableRange(double value, double average, double deviation) {
        double allowedDiff = Math.abs(average) * deviation;
        double min = average - allowedDiff;
        double max = average + allowedDiff;

        return value >= min && value <= max;
    }
}
