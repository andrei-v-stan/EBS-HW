package org.example.models;

import org.example.models.enums.Field;
import org.example.models.enums.Operator;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Condition implements Serializable {
    private Field field;
    private Operator operator;
    private Object value;

    public Condition() {

    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        var dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        var val = value;
        if (field == Field.company) {
            val = "\"" + val + "\"";
        }
        else if(field == Field.date) {
            val = dateFormat.format(val);
        }

        return "(" +
                field.name() + "," +
                operator.label + "," +
                val +
                ')';
    }

    public Field getField() {
        return field;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }
}
