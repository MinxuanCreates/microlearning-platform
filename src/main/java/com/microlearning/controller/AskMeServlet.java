package com.microlearning.controller;

import com.microlearning.model.LearningIntent;
import com.microlearning.service.LearningIntentClassifier;
import com.microlearning.util.ThymeleafUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

/**
 * Gives learning guidance based on the user's question.
 */
@WebServlet("/ask-me")
public class AskMeServlet extends HttpServlet {

    private final LearningIntentClassifier classifier =
            new LearningIntentClassifier();

    /**
     * Handles Ask Me Here questions.
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Read the user's question.
        String question = request.getParameter("question");

        // Check whether the user is logged in.
        HttpSession session = request.getSession(false);

        boolean loggedIn =
                session != null
                        && session.getAttribute("userId") != null;

        String answer;
        String recommendedModule;
        Integer recommendedModuleId;
        LearningIntent detectedIntent =
                LearningIntent.UNKNOWN;

        // Check whether the question is empty.
        if (question == null || question.trim().isEmpty()) {

            answer =
                    "Please tell us what you would like help with.";

            recommendedModule = null;
            recommendedModuleId = null;

        } else {

            String trimmedQuestion = question.trim();

            /*
             * Use NLP similarity matching to identify
             * the user's main learning need.
             */
            detectedIntent =
                    classifier.classify(trimmedQuestion);

            if (detectedIntent
                    == LearningIntent.FIRST_JAVA_PROGRAM) {

                answer =
                        "Creating a Hello World program is a helpful "
                                + "first step. It introduces how to write, "
                                + "run and understand a simple Java program.";

                recommendedModule =
                        "Java Basics - My First Program";

                recommendedModuleId = 3;

            } else if (detectedIntent
                    == LearningIntent.CYBER_SECURITY) {

                answer =
                        "You can begin with basic cybersecurity concepts "
                                + "and learn simple ways to protect your "
                                + "information and stay safer online.";

                recommendedModule =
                        "Cyber Security Basics - Introduction";

                recommendedModuleId = 2;

            } else if (detectedIntent
                    == LearningIntent.BEGINNER_PROGRAMMING) {

                answer =
                        "A helpful starting point is a short introduction "
                                + "to Java. It introduces basic programming "
                                + "ideas for learners with little or no "
                                + "previous experience.";

                recommendedModule =
                        "Java Basics - Introduction";

                recommendedModuleId = 1;

            } else {

                /*
                 * Keep simple guidance for questions that are
                 * not covered by the NLP learning intents.
                 */
                String lowerQuestion =
                        trimmedQuestion.toLowerCase();

                // Support learners who feel unsure.
                if (lowerQuestion.contains("nervous")
                        || lowerQuestion.contains("confidence")
                        || lowerQuestion.contains("afraid")
                        || lowerQuestion.contains("worried")
                        || lowerQuestion.contains("overwhelmed")
                        || lowerQuestion.contains("not confident")) {

                    answer =
                            "It is normal to feel unsure when learning "
                                    + "something new. Start with one short "
                                    + "introductory lesson and continue at "
                                    + "your own pace.";

                    recommendedModule =
                            "Java Basics - Introduction";

                    recommendedModuleId = 1;

                    // Support learners with limited time.
                } else if (lowerQuestion.contains("limited time")
                        || lowerQuestion.contains("not much time")
                        || lowerQuestion.contains("busy")
                        || lowerQuestion.contains("short lesson")
                        || lowerQuestion.contains("few minutes")) {

                    answer =
                            "Choose one short lesson and focus on a single "
                                    + "idea. You can pause and return when "
                                    + "you have time.";

                    recommendedModule =
                            "Java Basics - Introduction";

                    recommendedModuleId = 1;

                    // Give note-taking guidance.
                } else if (lowerQuestion.contains("note")
                        || lowerQuestion.contains("remember")
                        || lowerQuestion.contains("revision")
                        || lowerQuestion.contains("review")) {

                    answer =
                            "Writing a short note after each lesson can help "
                                    + "you remember important ideas and return "
                                    + "to your learning later.";

                    recommendedModule = null;
                    recommendedModuleId = null;

                    // Give Community guidance.
                } else if (lowerQuestion.contains("community")
                        || lowerQuestion.contains("support from others")
                        || lowerQuestion.contains("other learners")
                        || lowerQuestion.contains("share experience")
                        || lowerQuestion.contains("ask other people")) {

                    answer =
                            "You can explore the Community to read shared "
                                    + "experiences, ask questions and connect "
                                    + "with other learners.";

                    recommendedModule = null;
                    recommendedModuleId = null;

                } else {

                    // No suitable learning intent was found.
                    answer =
                            "I could not identify a suitable learning topic. "
                                    + "Try describing whether you are new to "
                                    + "programming, want to write your first "
                                    + "Java program, or are interested in "
                                    + "cyber security.";

                    recommendedModule = null;
                    recommendedModuleId = null;
                }
            }
        }

        // Create the Thymeleaf application.
        JakartaServletWebApplication application =
                JakartaServletWebApplication.buildApplication(
                        getServletContext());

        // Get the shared template engine.
        TemplateEngine templateEngine =
                ThymeleafUtil.getTemplateEngine(
                        getServletContext());

        // Create the page context.
        WebContext context =
                new WebContext(
                        application.buildExchange(
                                request,
                                response),
                        request.getLocale());

        // Send the result to the page.
        context.setVariable("question", question);
        context.setVariable("answer", answer);

        context.setVariable(
                "recommendedModule",
                recommendedModule);

        context.setVariable(
                "recommendedModuleId",
                recommendedModuleId);

        context.setVariable(
                "detectedIntent",
                detectedIntent);

        context.setVariable("loggedIn", loggedIn);

        // Display the result page.
        templateEngine.process(
                "ask-me-result",
                context,
                response.getWriter());
    }
}