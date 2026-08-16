package com.microlearning.controller;

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
 * Displays the notes page.
 */
@WebServlet("/notes")
public class NotesServlet extends HttpServlet {

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

        templateEngine.process("notes", context, response.getWriter());
    }
}