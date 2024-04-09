package org.example.models;

import java.io.Serializable;
import java.util.List;

public class Subscription implements Serializable {
    private final List<Condition> conditions;

    public Subscription(List<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        StringBuilder contentBuilder = new StringBuilder();
        for (var condition : conditions) {
            contentBuilder.append(condition).append(";");
        }

        return "{" + contentBuilder + '}';
    }

    public List<Condition> getConditions() {
        return conditions;
    }
}
