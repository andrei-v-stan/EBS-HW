package org.example.business.generators;

import org.example.config.Configuration;
import org.example.config.Limit;
import org.example.config.SubscriptionWorkerConfig;
import org.example.models.Condition;
import org.example.models.Subscription;
import org.example.models.enums.Field;
import org.example.models.enums.Operator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SubscriptionGenerator {
    private final List<Operator> operatorEnumValues;
    private final Random random;

    public SubscriptionGenerator() {
        this.operatorEnumValues = Arrays.asList(Operator.values());
        this.random = new Random();
    }

    public List<Subscription> generateMultiple(Configuration configuration, SubscriptionWorkerConfig subWorkerConfig) {
        var subs = new ArrayList<Subscription>();
        for (var i = 0; i < subWorkerConfig.getQuantum(); i++) {
            subs.add(generate(configuration, subWorkerConfig, i));
        }

        return subs;
    }

    public Subscription generate(Configuration config, SubscriptionWorkerConfig subWorkerConfig, int index) {
        var conditions = new ArrayList<Condition>();
        for (var field : Field.values()) {
            var minimumWithField = subWorkerConfig.getWithFieldsCounts().getOrDefault(field, null);
            var minimumWithEqual = subWorkerConfig.getWithEqualCounts().getOrDefault(field, null);

            // if field appears
            if ((minimumWithField != null && index < minimumWithField) || random.nextBoolean()) {
                // set field
                var condition = new Condition();
                condition.setField(field);

                // set operator
                if ((minimumWithEqual != null && index < minimumWithEqual) || random.nextBoolean()) {
                    condition.setOperator(Operator.Eq);
                }
                else {
                    condition.setOperator(getRandomOperator());
                }

                // set value
                if(field == Field.company) {
                    condition.setValue(getRandomCompany(config));
                }
                else if(field == Field.date) {
                    var limit = config.limits().get(Field.date);
                    condition.setValue(getRandomDate(limit));
                }
                else {
                    var limit = config.limits().get(field);
                    condition.setValue(getRandomDouble(limit));
                }

                conditions.add(condition);
            }
        }

        return new Subscription(conditions);
    }

    private Operator getRandomOperator() {
        var length = operatorEnumValues.size();
        return operatorEnumValues.get(random.nextInt(length));
    }

    private String getRandomCompany(Configuration configuration) {
        var index = ThreadLocalRandom.current().nextInt(configuration.companies().size());
        return configuration.companies().get(index);
    }

    private double getRandomDouble(Limit limit) {
        var min = (double) limit.min;
        var max = (double) limit.max;
        var value  = min + (max - min) * ThreadLocalRandom.current().nextDouble();

        return Math.floor(value * 100) / 100;
    }

    private Date getRandomDate(Limit limit) {
        var min = (Date) limit.min;
        var max = (Date) limit.max;
        var time = ThreadLocalRandom.current().nextLong(min.getTime(), max.getTime());

        return new Date(time);
    }
}
