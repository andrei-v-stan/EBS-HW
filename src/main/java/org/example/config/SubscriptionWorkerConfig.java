package org.example.config;

import org.example.models.enums.Field;

import java.util.Map;

public class SubscriptionWorkerConfig {
    private int quantum;
    private Map<Field, Integer> withFieldsCounts;
    private Map<Field, Integer> withEqualCounts;

    public SubscriptionWorkerConfig() {

    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public Map<Field, Integer> getWithFieldsCounts() {
        return withFieldsCounts;
    }

    public void setWithFieldsCounts(Map<Field, Integer> withFieldsCounts) {
        this.withFieldsCounts = withFieldsCounts;
    }

    public Map<Field, Integer> getWithEqualCounts() {
        return withEqualCounts;
    }

    public void setWithEqualCounts(Map<Field, Integer> withEqualCounts) {
        this.withEqualCounts = withEqualCounts;
    }
}
