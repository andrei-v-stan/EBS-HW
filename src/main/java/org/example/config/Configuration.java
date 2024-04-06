package org.example.config;

import org.example.models.enums.Field;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Configuration {
    private final int publicationCount;
    private final int subscriptionCount;
    private final Map<Field, Double> weights;
    private final Map<Field, Limit> limits;
    private final List<String> companies;
    private final Map<Field, Double> equalWeights;
    private final int threadCount;

    public Configuration(
            int publicationCount,
            int subscriptionCount,
            Map<Field, Double> weights,
            Map<Field, Limit> limits,
            List<String> companies,
            Map<Field, Double> equalWeights,
            int threadCount
    ) {
        this.publicationCount = publicationCount;
        this.subscriptionCount = subscriptionCount;
        this.validateWeights(weights);
        this.weights = weights;
        this.limits = limits;
        this.companies = companies;
        this.validateEqualWeights(weights, equalWeights);
        this.equalWeights = equalWeights;
        this.threadCount = threadCount;
    }

    public int publicationCount() {
        return publicationCount;
    }

    public int subscriptionCount() {
        return subscriptionCount;
    }

    public Map<Field, Double> weights() {
        return weights;
    }

    public Map<Field, Limit> limits() {
        return limits;
    }

    public List<String> companies() {
        return companies;
    }

    public Map<Field, Double> equalFrequencyMinimums() {
        return equalWeights;
    }

    public int threadCount() {
        return threadCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Configuration) obj;
        return this.publicationCount == that.publicationCount &&
                this.subscriptionCount == that.subscriptionCount &&
                Objects.equals(this.weights, that.weights) &&
                Objects.equals(this.limits, that.limits) &&
                Objects.equals(this.companies, that.companies) &&
                Objects.equals(this.equalWeights, that.equalWeights) &&
                this.threadCount == that.threadCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicationCount, subscriptionCount, weights, limits, companies, equalWeights, threadCount);
    }

    @Override
    public String toString() {
        return "Configuration[\n" +
                "\tpublicationCount=" + publicationCount + ",\n" +
                "\tsubscriptionCount=" + subscriptionCount + ",\n" +
                "\tweights=" + weights + ",\n" +
                "\tlimits=" + limits + ",\n" +
                "\tcompanies=" + companies + ",\n" +
                "\tequalWeights=" + equalWeights + ",\n" +
                "\tthreadCount=" + threadCount + "\n]\n";
    }

    private void validateWeights(Map<Field, Double> weights) {
        for (var weight : weights.entrySet()) {
            if (weight.getValue() < 0 || weight.getValue() > 1) {
                throw new InvalidParameterException("The weight of the field must be a number in the [0, 1] interval");
            }
        }
    }

    private void validateEqualWeights(Map<Field, Double> fieldWeights, Map<Field, Double> equalWeights) {
        for (var equalWeight : equalWeights.entrySet()) {
            var fieldWeight = fieldWeights.getOrDefault(equalWeight.getKey(), null);
            if (fieldWeight == null) {
                var message = "The minimum frequency of the equal operator on the '"
                        + equalWeight.getKey()
                        + "' field cannot be ensured because the field weight is not specified";
                throw new InvalidParameterException(message);
            }
            if (fieldWeight < equalWeight.getValue()) {
                var message = "The minimum frequency of the equal operator on the '"
                        + equalWeight.getKey()
                        + "' field cannot be greater than the field";
                throw new InvalidParameterException(message);
            }
        }
    }
}

