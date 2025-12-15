package com.hs.WellnessBookingSystem.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EventStatus {
    PENDING,
    APPROVED,
    REJECTED;

    @JsonCreator
    public static EventStatus fromString(String value) {
        return value == null ? null : EventStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
