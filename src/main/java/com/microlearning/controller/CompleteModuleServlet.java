package com.microlearning.controller;

import com.microlearning.dao.LearningProgressDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Marks a learning module as completed for the logged-in user.
 */
@WebServlet("/complete-module")
public class CompleteModuleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        String moduleIdParam = request.getParameter("moduleId");

        if (moduleIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/modules");
            return;
        }

        try {
            int moduleId = Integer.parseInt(moduleIdParam);

            LearningProgressDAO progressDAO = new LearningProgressDAO();
            progressDAO.markModuleCompleted(userId, moduleId);

            response.sendRedirect(request.getContextPath() + "/dashboard");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/modules");
        }
    }
}