package org.example.business.generators;

import org.example.config.Configuration;
import org.example.config.Limit;
import org.example.models.Publication;
import org.example.models.enums.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PublicationGenerator {
    public List<Publication> generateMultiple(Configuration configuration, int count) {
        var pubs = new ArrayList<Publication>();
        for (var i = 0; i < count; i++) {
            pubs.add(generate(configuration));
        }

        return pubs;
    }

    public Publication generate(Configuration configuration) {
        var company = getRandomCompany(configuration);

        var value = getRandomDouble(configuration.limits().get(Field.value));

        var drop = getRandomDouble(configuration.limits().get(Field.drop));

        var variation = getRandomDouble(configuration.limits().get(Field.variation));

        var date = getRandomDate(configuration.limits().get(Field.date));

        return new Publication(company, value, drop, variation, date);
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
