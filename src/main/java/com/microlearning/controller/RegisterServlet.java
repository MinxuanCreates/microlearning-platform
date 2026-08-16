package com.microlearning.controller;

import com.microlearning.dao.UserDAO;
import com.microlearning.model.User;
import com.microlearning.util.ThymeleafUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

/**
 * Handles user registration.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        TemplateEngine templateEngine =
                ThymeleafUtil.getTemplateEngine(getServletContext());

        JakartaServletWebApplication application =
                JakartaServletWebApplication.buildApplication(getServletContext());

        WebContext context =
                new WebContext(application.buildExchange(request, response));

        templateEngine.process("register", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User(username, email, password);
        UserDAO userDAO = new UserDAO();

        boolean success = userDAO.registerUser(user);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/login?registered=true");
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");

            TemplateEngine templateEngine =
                    ThymeleafUtil.getTemplateEngine(getServletContext());

            JakartaServletWebApplication application =
                    JakartaServletWebApplication.buildApplication(getServletContext());

            WebContext context =
                    new WebContext(application.buildExchange(request, response));

            context.setVariable("error", "Registration failed. This email may already be registered.");

            templateEngine.process("register", context, response.getWriter());
        }
    }
}