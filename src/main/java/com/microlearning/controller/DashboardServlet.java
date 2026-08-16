package com.microlearning.controller;

import com.microlearning.dao.LearningProgressDAO;
import com.microlearning.dao.ModuleDAO;
import com.microlearning.model.LearningProgress;
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
import java.util.ArrayList;

/**
 * Displays the learner dashboard.
 * This page is the main entry point after login.
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        // If the user is not logged in, redirect to login page
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");

        LearningProgressDAO progressDAO = new LearningProgressDAO();

        ArrayList<LearningProgress> progressList =
                progressDAO.getProgressByUserId(userId);

        int completedCount =
                progressDAO.countCompletedModules(userId);

        ModuleDAO moduleDAO = new ModuleDAO();
        int totalModules = moduleDAO.countAllModules();

        int progressPercentage = 0;

        if (totalModules > 0) {
            progressPercentage = (completedCount * 100) / totalModules;
        }

        String recommendation;

        if (completedCount == 0) {
            recommendation = "Start with Java Basics and create a note after the lesson.";
        } else if (completedCount == 1) {
            recommendation = "Great progress. Try completing the next topic and share a question in the community.";
        } else {
            recommendation = "Excellent work. Review your notes and continue exploring community discussions.";
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        TemplateEngine templateEngine =
                ThymeleafUtil.getTemplateEngine(getServletContext());

        JakartaServletWebApplication application =
                JakartaServletWebApplication.buildApplication(getServletContext());

        WebContext context = new WebContext(
                application.buildExchange(request, response),
                request.getLocale()
        );

        context.setVariable("username", username);
        context.setVariable("progressList", progressList);
        context.setVariable("completedCount", completedCount);
        context.setVariable("totalModules", totalModules);
        context.setVariable("progressPercentage", progressPercentage);
        context.setVariable("recommendation", recommendation);

        templateEngine.process("dashboard", context, response.getWriter());
    }
}