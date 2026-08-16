package com.microlearning.controller;

import com.microlearning.dao.SharedKnowledgeDAO;
import com.microlearning.model.SharedKnowledge;
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
 * Displays one shared knowledge post.
 */
@WebServlet("/shared-knowledge-details")
public class SharedKnowledgeDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        boolean loggedIn =
                session != null
                        && session.getAttribute("userId") != null;

        String knowledgeIdParam =
                request.getParameter("knowledgeId");

        if (knowledgeIdParam == null
                || knowledgeIdParam.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/modules");

            return;
        }

        int knowledgeId;

        try {
            knowledgeId =
                    Integer.parseInt(knowledgeIdParam);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() + "/modules");

            return;
        }

        SharedKnowledgeDAO sharedKnowledgeDAO =
                new SharedKnowledgeDAO();

        SharedKnowledge knowledge =
                sharedKnowledgeDAO.getSharedKnowledgeById(
                        knowledgeId);

        if (knowledge == null) {

            response.sendRedirect(
                    request.getContextPath() + "/modules");

            return;
        }

        TemplateEngine templateEngine =
                ThymeleafUtil.getTemplateEngine(
                        getServletContext());

        JakartaServletWebApplication application =
                JakartaServletWebApplication.buildApplication(
                        getServletContext());

        WebContext context =
                new WebContext(
                        application.buildExchange(
                                request,
                                response),
                        request.getLocale());

        context.setVariable("loggedIn", loggedIn);
        context.setVariable("knowledge", knowledge);

        templateEngine.process(
                "shared-knowledge-details",
                context,
                response.getWriter());
    }
}