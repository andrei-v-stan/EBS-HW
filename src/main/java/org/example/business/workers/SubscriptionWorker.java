package org.example.business.workers;

import org.example.business.generators.SubscriptionGenerator;
import org.example.config.Configuration;
import org.example.config.SubscriptionWorkerConfig;
import org.example.models.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SubscriptionWorker extends Thread {
    private final CountDownLatch latch;
    private final SubscriptionWorkerConfig subWorkerConfig;
    private final Configuration configuration;
    private final SubscriptionGenerator subscriptionGenerator;
    private List<Subscription> subscriptions;

    public SubscriptionWorker(String name, CountDownLatch latch, Configuration config, SubscriptionWorkerConfig subWorkerConfig) {
        setName(name);
        this.latch = latch;
        this.configuration = config;
        this.subWorkerConfig = subWorkerConfig;
        this.subscriptionGenerator = new SubscriptionGenerator();
        this.subscriptions = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            latch.await();
            subscriptions = subscriptionGenerator.generateMultiple(configuration, subWorkerConfig);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }
}
