package org.example.business.workers;

import org.example.business.services.FileLogger;
import org.example.config.Configuration;
import org.example.models.Publication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

public class PublicationWorkerManager {
    public static void generatePublications(Configuration config, FileLogger fileLogger) throws InterruptedException, IOException {
        var latch = new CountDownLatch(1);
        var publicationWorkers = getPublicationWorkers(config, latch);

        var startTime = System.currentTimeMillis();
        latch.countDown();

        List<Publication> totalPublications = new ArrayList<>();
        for (var publicationWorker : publicationWorkers) {
            publicationWorker.join();
            totalPublications = Stream.concat(totalPublications.stream(), publicationWorker.getPublications().stream()).toList();
        }

        var endTime = System.currentTimeMillis();

        fileLogger.log("Publications:").newLine();
        fileLogger.log("Generation time: ").logTime(startTime, endTime).newLine();
        fileLogger.logList(totalPublications).newLine();
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
