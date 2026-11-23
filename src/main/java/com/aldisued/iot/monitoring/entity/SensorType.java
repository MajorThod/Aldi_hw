package com.aldisued.iot.monitoring.entity;

public enum SensorType {

    TEMPERATURE(-20.0, 40.0, "Temperature out of range: "),
    HUMIDITY(null, 90.0, "Humidity too high: "),
    ATMOSPHERIC_PRESSURE(950.0, 1050.0, "Pressure out of range: ");

    private final Double min;
    private final Double max;
    private final String messagePrefix;

    SensorType(Double min, Double max, String messagePrefix) {
        this.min = min;
        this.max = max;
        this.messagePrefix = messagePrefix;
    }

    public boolean isRuleViolated(double value) {
        boolean isBelowMin = min != null && value < min;
        boolean isAboveMax = max != null && value > max;

        return isBelowMin || isAboveMax;
    }

    public String getMessagePrefix() {
        return messagePrefix;
    }
}