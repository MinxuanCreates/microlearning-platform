package com.microlearning.service;

public class LearningIntentClassifierTest {

    public static void main(String[] args) {

        LearningIntentClassifier classifier =
                new LearningIntentClassifier();

        System.out.println(
                "Test 1: " +
                        classifier.classify(
                                "I have no technical background and need an easy place to begin."
                        )
        );

        System.out.println(
                "Test 2: " +
                        classifier.classify(
                                "I do not know anything about programming."
                        )
        );

        System.out.println(
                "Test 3: " +
                        classifier.classify(
                                "I would like to understand how a simple Java program works."
                        )
        );

        System.out.println(
                "Test 4: " +
                        classifier.classify(
                                "Show me how Hello World works."
                        )
        );

        System.out.println(
                "Test 5: " +
                        classifier.classify(
                                "How do I protect my password online?"
                        )
        );

        System.out.println(
                "Test 6: " +
                        classifier.classify(
                                "I love travelling."
                        )
        );

        System.out.println(
                "Test 7: " +
                        classifier.classify(
                                "My favourite food is pizza."
                        )
        );
    }
}