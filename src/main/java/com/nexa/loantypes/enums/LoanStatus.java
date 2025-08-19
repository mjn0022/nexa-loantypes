package com.nexa.loantypes.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LoanStatus {
    active, inactive; // lowercase to match DB check

    @JsonCreator
    public static LoanStatus from(String value) {
        if (value == null) return null;
        try {
            return LoanStatus.valueOf(value.trim().toLowerCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid status. Allowed: active, inactive");
        }
    }

    @JsonValue
    public String toJson() {
        return name();
    }
}
