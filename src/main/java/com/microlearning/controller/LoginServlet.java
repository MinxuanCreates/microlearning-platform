package com.microlearning.controller;

import com.microlearning.dao.UserDAO;
import com.microlearning.model.User;
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
 * Handles user login.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

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

        String registered = request.getParameter("registered");

        if ("true".equals(registered)) {
            context.setVariable("message", "Registration successful. Please log in.");
        }

        templateEngine.process("login", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.loginUser(email, password);

        if (user != null) {
            HttpSession session = request.getSession();

            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("email", user.getEmail());

            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
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

            context.setVariable("error", "Invalid email or password.");

            templateEngine.process("login", context, response.getWriter());
        }
    }
}