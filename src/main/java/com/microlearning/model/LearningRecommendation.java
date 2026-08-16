package com.microlearning.model;

/**
 * Stores the result of a learning recommendation.
 */
public class LearningRecommendation {

    private final LearningIntent intent;
    private final String moduleName;
    private final String reason;
    private final String moduleUrl;
    private final double similarityScore;

    public LearningRecommendation(
            LearningIntent intent,
            String moduleName,
            String reason,
            String moduleUrl,
            double similarityScore
    ) {
        this.intent = intent;
        this.moduleName = moduleName;
        this.reason = reason;
        this.moduleUrl = moduleUrl;
        this.similarityScore = similarityScore;
    }

    public LearningIntent getIntent() {
        return intent;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getReason() {
        return reason;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }
}