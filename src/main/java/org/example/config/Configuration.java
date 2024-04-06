package org.example.config;

import org.example.models.enums.Field;

import java.util.List;
import java.util.Map;

public record Configuration(
        int publicationCount,
        int subscriptionCount,
        Map<Field, Double> weights,
        Map<Field, Limit> limits,
        List<String> companies,
        Map<Field, Double> equalFrequencyMinimums,
        int threadCount
) {
}

