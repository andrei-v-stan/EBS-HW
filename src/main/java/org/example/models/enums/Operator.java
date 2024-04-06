package org.example.models.enums;

public enum Operator {
    Eq("="),
    Neq("!="),
    Gt(">"),
    Geq(">="),
    Lt("<"),
    Leq("<=");

    public final String label;

    Operator(String label) {
        this.label = label;
    }
}
