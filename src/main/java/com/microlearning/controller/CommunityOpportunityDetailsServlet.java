package com.microlearning.controller;

import com.microlearning.dao.CommunityOpportunityDAO;
import com.microlearning.model.CommunityOpportunity;
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
 * Displays one community opportunity.
 */
@WebServlet("/community-opportunity")
public class CommunityOpportunityDetailsServlet extends HttpServlet {

    /**
     * Loads one opportunity from the database.
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session =
                request.getSession(false);

        boolean loggedIn =
                session != null
                        && session.getAttribute("userId") != null;

        String opportunityIdValue =
                request.getParameter("opportunityId");

        if (opportunityIdValue == null
                || opportunityIdValue.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/community-opportunities"
            );
            return;
        }

        int opportunityId;

        try {
            opportunityId =
                    Integer.parseInt(opportunityIdValue);
        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/community-opportunities"
            );
            return;
        }

        CommunityOpportunityDAO opportunityDAO =
                new CommunityOpportunityDAO();

        CommunityOpportunity opportunity =
                opportunityDAO.getOpportunityById(opportunityId);

        if (opportunity == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Community opportunity not found."
            );
            return;
        }

        TemplateEngine templateEngine =
                ThymeleafUtil.getTemplateEngine(
                        getServletContext()
                );

        JakartaServletWebApplication application =
                JakartaServletWebApplication.buildApplication(
                        getServletContext()
                );

        WebContext context =
                new WebContext(
                        application.buildExchange(
                                request,
                                response
                        ),
                        request.getLocale()
                );

        context.setVariable(
                "opportunity",
                opportunity
        );

        context.setVariable(
                "loggedIn",
                loggedIn
        );

        if (loggedIn) {
            context.setVariable(
                    "username",
                    session.getAttribute("username")
            );
        }

        templateEngine.process(
                "community-opportunity-details",
                context,
                response.getWriter()
        );
    }
}