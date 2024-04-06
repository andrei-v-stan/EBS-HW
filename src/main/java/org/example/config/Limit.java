package org.example.config;

import java.security.InvalidParameterException;

public class Limit {
    public Object min;
    public Object max;

    public Limit(Object min, Object max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return "[" + min + "," + max + "]";
    }
}
