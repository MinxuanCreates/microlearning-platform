package com.microlearning.service;

import com.microlearning.model.LearningIntent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LearningIntentClassifier {

    /**
     * Minimum similarity score required before
     * a learning intent is accepted.
     */
    private static final double MINIMUM_SIMILARITY = 0.20;

    /**
     * Example natural-language queries for each supported learning intent.
     */
    private static final Map<LearningIntent, List<String>> INTENT_EXAMPLES =
            Map.of(
                    LearningIntent.BEGINNER_PROGRAMMING,
                    List.of(
                            "I am completely new to coding",
                            "I have never programmed before",
                            "I do not know where to start",
                            "Programming is new to me",
                            "I have no computing experience",
                            "I need an easy beginner lesson",
                            "Coding feels difficult",
                            "What should I learn first"
                    ),

                    LearningIntent.FIRST_JAVA_PROGRAM,
                    List.of(
                            "I want to write my first Java program",
                            "How do I create Hello World",
                            "I want to understand simple Java code",
                            "How can I print text in Java",
                            "Show me a basic Java program",
                            "I want to learn Java syntax",
                            "I want to create my first piece of code",
                            "How does a Java program begin",
                            "How does Hello World work",
                            "Explain Hello World",
                            "Show me how Hello World works"
                    ),

                    LearningIntent.CYBER_SECURITY,
                    List.of(
                            "I want to learn cyber security",
                            "How can I stay safe online",
                            "I want to understand passwords",
                            "What is a cyber attack",
                            "Teach me about online safety",
                            "How can I protect my information",
                            "I am interested in computer security",
                            "I want to learn about digital safety"
                    )
            );

    /**
     * Common words that are removed because they usually
     * carry little meaning for this small classification task.
     */
    private static final Set<String> STOP_WORDS =
            Set.of(
                    "i", "am", "is", "are", "the", "a", "an",
                    "to", "of", "in", "on", "and", "or",
                    "do", "does", "did", "me", "my", "how",
                    "what", "can", "want", "would", "like",
                    "learn", "learning"
            );

    /**
     * Simple word normalisation used to treat similar words
     * as the same concept.
     */
    private static final Map<String, String> NORMALISATIONS =
            Map.ofEntries(
                    Map.entry("coding", "programming"),
                    Map.entry("code", "programming"),
                    Map.entry("codes", "programming"),
                    Map.entry("begin", "start"),
                    Map.entry("beginning", "start"),
                    Map.entry("starting", "start"),
                    Map.entry("safety", "security"),
                    Map.entry("safe", "security"),
                    Map.entry("secure", "security"),
                    Map.entry("passwords", "password")
            );

    /**
     * Classifies a user's natural-language query
     * into the most similar learning intent.
     */
    public LearningIntent classify(String userInput) {

        if (userInput == null || userInput.isBlank()) {
            return LearningIntent.UNKNOWN;
        }

        Map<String, Integer> userVector =
                createFrequencyVector(
                        tokenize(userInput)
                );

        LearningIntent bestIntent =
                LearningIntent.UNKNOWN;

        double bestScore = 0.0;

        for (Map.Entry<LearningIntent, List<String>> entry
                : INTENT_EXAMPLES.entrySet()) {

            String intentProfile =
                    String.join(" ", entry.getValue());

            Map<String, Integer> intentVector =
                    createFrequencyVector(
                            tokenize(intentProfile)
                    );

            double score =
                    cosineSimilarity(
                            userVector,
                            intentVector
                    );

            if (score > bestScore) {
                bestScore = score;
                bestIntent = entry.getKey();
            }
        }

        if (bestScore < MINIMUM_SIMILARITY) {
            return LearningIntent.UNKNOWN;
        }

        return bestIntent;
    }

    /**
     * Cleans and tokenises natural-language text.
     */
    private List<String> tokenize(String text) {

        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }

        String cleaned = text
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", " ");

        String[] words = cleaned.split("\\s+");

        List<String> tokens = new ArrayList<>();

        for (String word : words) {

            if (word.isBlank() || STOP_WORDS.contains(word)) {
                continue;
            }

            String normalised =
                    NORMALISATIONS.getOrDefault(word, word);

            tokens.add(normalised);
        }

        return tokens;
    }

    /**
     * Converts a list of tokens into a simple word-frequency vector.
     */
    private Map<String, Integer> createFrequencyVector(
            List<String> tokens
    ) {

        Map<String, Integer> vector = new HashMap<>();

        for (String token : tokens) {

            int currentCount =
                    vector.getOrDefault(token, 0);

            vector.put(token, currentCount + 1);
        }

        return vector;
    }

    /**
     * Calculates cosine similarity between two word-frequency vectors.
     */
    private double cosineSimilarity(
            Map<String, Integer> first,
            Map<String, Integer> second
    ) {

        Set<String> vocabulary = new HashSet<>();

        vocabulary.addAll(first.keySet());
        vocabulary.addAll(second.keySet());

        double dotProduct = 0.0;
        double firstMagnitude = 0.0;
        double secondMagnitude = 0.0;

        for (String word : vocabulary) {

            int firstValue =
                    first.getOrDefault(word, 0);

            int secondValue =
                    second.getOrDefault(word, 0);

            dotProduct += firstValue * secondValue;

            firstMagnitude +=
                    firstValue * firstValue;

            secondMagnitude +=
                    secondValue * secondValue;
        }

        if (firstMagnitude == 0.0
                || secondMagnitude == 0.0) {

            return 0.0;
        }

        return dotProduct /
                (Math.sqrt(firstMagnitude)
                        * Math.sqrt(secondMagnitude));
    }
}