package org.example.business.workers;

import org.example.config.Configuration;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class PublicationWorkerManager {
    public static void generatePublications(Configuration config) throws InterruptedException {
        var latch = new CountDownLatch(1);
        var publicationWorkers = getPublicationWorkers(config, latch);

        latch.countDown();

        System.out.println("Publications:");
        for (var publicationWorker : publicationWorkers) {
            publicationWorker.join();
            for (var pub : publicationWorker.getPublications()) {
                System.out.println(pub);
            }
        }
    }

    private static ArrayList<PublicationWorker> getPublicationWorkers(Configuration config, CountDownLatch latch) {
        var publicationWorkers = new ArrayList<PublicationWorker>();
        for (var i = 0; i < config.threadCount(); i++) {
            int count = config.publicationCount() / config.threadCount();
            if(i == config.threadCount() - 1) {
                count += config.publicationCount() % config.threadCount();
            }
            var publicationWorker = new PublicationWorker("Publication worker " + i, latch, config, count);
            publicationWorker.start();
            publicationWorkers.add(publicationWorker);
        }
        return publicationWorkers;
    }
}
