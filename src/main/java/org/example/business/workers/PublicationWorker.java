package org.example.business.workers;

import org.example.business.generators.PublicationGenerator;
import org.example.config.Configuration;
import org.example.models.Publication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class PublicationWorker extends Thread {
    private final CountDownLatch latch;
    private final int count;
    private final Configuration configuration;
    private final PublicationGenerator publicationGenerator;
    private volatile List<Publication> publications;

    public PublicationWorker(String name, CountDownLatch latch, Configuration configuration, int count) {
        setName(name);
        this.latch = latch;
        this.count = count;
        this.configuration = configuration;
        publicationGenerator = new PublicationGenerator();
        this.publications = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            latch.await();
            publications = publicationGenerator.generateMultiple(configuration, count);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Publication> getPublications() {
        return publications;
    }
}
