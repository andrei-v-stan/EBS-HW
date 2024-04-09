package org.example.business.workers.tests;

import org.example.business.services.FileLogger;
import org.example.business.workers.PublicationWorkerManager;
import org.example.config.Configuration;
import org.example.config.Limit;
import org.example.models.Publication;
import org.example.models.enums.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InvalidClassException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PublicationWorkerManagerTest {

    private Configuration config;
    private FileLogger fileLogger;

    @Before
    public void setUp() throws IOException, ParseException {
        this.config = getConfiguration();

        this.fileLogger = new FileLogger(config);
        fileLogger.log(config.toString());
    }

    @Test
    public void testGeneratePublications() throws IOException, InterruptedException {
        // act
        var result = PublicationWorkerManager.generatePublications(config, fileLogger);

        // assert
        Assert.assertTrue(areValid(result, config));
    }

    private static boolean areValid(List<Publication> publications, Configuration config) throws InvalidClassException {
        if (publications.size() != config.publicationCount()) {
            return false;
        }

        for (var pub : publications) {
            if(!isValid(pub, config)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isValid(Publication pub, Configuration config) throws InvalidClassException {
        if (!config.companies().contains(pub.getCompany())) {
            return false;
        }
        if (!config.limits().get(Field.value).contains(pub.getValue())) {
            return false;
        }
        if (!config.limits().get(Field.drop).contains(pub.getDrop())) {
            return false;
        }
        if (!config.limits().get(Field.variation).contains(pub.getVariation())) {
            return false;
        }
        if (!config.limits().get(Field.date).contains(pub.getDate())) {
            return false;
        }

        return true;
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

        var threadCount = 4;
//        return new Configuration(10, 100, weights, limits, companies, equalFrequencyMinimums, threadCount);
        return new Configuration(100, 1000, weights, limits, companies, equalFrequencyMinimums, threadCount);
//        return new Configuration(500, 5000, weights, limits, companies, equalFrequencyMinimums, threadCount);
//        return new Configuration(1000, 10000, weights, limits, companies, equalFrequencyMinimums, threadCount);
//        return new Configuration(5000, 50000, weights, limits, companies, equalFrequencyMinimums, threadCount);
//        return new Configuration(10000, 100000, weights, limits, companies, equalFrequencyMinimums, threadCount);
//        return new Configuration(10000, 1000000, weights, limits, companies, equalFrequencyMinimums, threadCount);
//        return new Configuration(100000, 10000000, weights, limits, companies, equalFrequencyMinimums, threadCount);
    }
}