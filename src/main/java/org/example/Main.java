package org.example;

import org.example.business.services.FileLogger;
import org.example.business.workers.PublicationWorkerManager;
import org.example.business.workers.SubscriptionWorkerManager;
import org.example.config.Configuration;
import org.example.config.Limit;
import org.example.models.enums.Field;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws ParseException, InterruptedException, IOException {
        var config = getConfiguration();

        var fileLogger = new FileLogger(config);
        fileLogger.log(config.toString());

        PublicationWorkerManager.generatePublications(config, fileLogger);
        SubscriptionWorkerManager.generateSubscriptions(config, fileLogger);

        fileLogger.close();
    }

    private static Configuration getConfiguration() throws ParseException {
        var weights = new HashMap<Field, Double>() {{
            put(Field.company, 0.5);
        }};

        var dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        var limits = new HashMap<Field, Limit>() {{
            put(Field.value, new Limit(1.0, 100.0));
            put(Field.drop, new Limit(1.0, 100.0));
            put(Field.variation, new Limit(0.0, 1.0));
            put(Field.date, new Limit(dateFormat.parse("01.01.2023"), dateFormat.parse("01.01.2024")));
        }};

        var companies = new ArrayList<String>() {{
            add("Google");
            add("Amazon");
            add("Netflix");
            add("Microsoft");
        }};

        var equalFrequencyMinimums = new HashMap<Field, Double>() {{
            put(Field.company, 0.4);
        }};

        var threadCount = 1;
//        return new Configuration(10, 100, weights, limits, companies, equalFrequencyMinimums, threadCount);
//        return new Configuration(100, 1000, weights, limits, companies, equalFrequencyMinimums, threadCount);
//        return new Configuration(500, 5000, weights, limits, companies, equalFrequencyMinimums, threadCount);
//        return new Configuration(1000, 10000, weights, limits, companies, equalFrequencyMinimums, threadCount);
//        return new Configuration(5000, 50000, weights, limits, companies, equalFrequencyMinimums, threadCount);
//        return new Configuration(10000, 100000, weights, limits, companies, equalFrequencyMinimums, threadCount);
        return new Configuration(10000, 1000000, weights, limits, companies, equalFrequencyMinimums, threadCount);
    }
}