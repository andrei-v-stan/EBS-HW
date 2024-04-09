package org.example.config;

import java.io.InvalidClassException;
import java.security.InvalidParameterException;
import java.util.Date;

public class Limit {
    public Object min;
    public Object max;

    public Limit(Object min, Object max) {
        this.min = min;
        this.max = max;
    }

    public boolean contains(Object elem) throws InvalidClassException {
        if (elem instanceof Integer) {
            return (int) min <= (int) elem && (int) elem <= (int) max;
        }
        if (elem instanceof Double) {
            return (double) min <= (double) elem && (double) elem <= (double) max;
        }
        if (elem instanceof Date) {
            return ((Date) min).getTime() <= ((Date) elem).getTime() && ((Date) elem).getTime() <= ((Date) max).getTime();
        }

        throw new InvalidClassException("Cannot compare limits to the element of the selected type.");
    }

    @Override
    public String toString() {
        return "[" + min + "," + max + "]";
    }
}

