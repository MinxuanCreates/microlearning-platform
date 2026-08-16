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
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Displays community opportunities and handles new submissions.
 * Visitors can view opportunities.
 * Only logged-in users can create them.
 */
@WebServlet("/community-opportunities")
public class CommunityOpportunityServlet extends HttpServlet {

    /**
     * Displays all opportunities or filters them by type.
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session =
                request.getSession(false);

        boolean loggedIn =
                session != null
                        && session.getAttribute("userId") != null;

        CommunityOpportunityDAO opportunityDAO =
                new CommunityOpportunityDAO();

        String selectedType =
                request.getParameter("type");

        List<CommunityOpportunity> opportunities;

        if (isValidOpportunityType(selectedType)) {

            opportunities =
                    opportunityDAO.getOpportunitiesByType(
                            selectedType
                    );

        } else {

            selectedType = "All";

            opportunities =
                    opportunityDAO.getAllOpportunities();
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

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
                "opportunities",
                opportunities
        );

        context.setVariable(
                "selectedType",
                selectedType
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

        String success =
                request.getParameter("success");

        if ("1".equals(success)) {
            context.setVariable(
                    "successMessage",
                    "Your community opportunity has been published."
            );
        }

        Object errorMessage =
                request.getAttribute("errorMessage");

        if (errorMessage != null) {
            context.setVariable(
                    "errorMessage",
                    errorMessage
            );
        }

        templateEngine.process(
                "community-opportunities",
                context,
                response.getWriter()
        );
    }

    /**
     * Saves a new opportunity.
     */
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session =
                request.getSession(false);

        // Only logged-in users can publish.
        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        int userId =
                (int) session.getAttribute("userId");

        String opportunityType =
                request.getParameter("opportunityType");

        String title =
                request.getParameter("title");

        String description =
                request.getParameter("description");

        String location =
                request.getParameter("location");

        String eventDateValue =
                request.getParameter("eventDate");

        String contactInformation =
                request.getParameter("contactInformation");

        // Check required fields.
        if (!isValidOpportunityType(opportunityType)
                || isBlank(title)
                || isBlank(description)) {

            request.setAttribute(
                    "errorMessage",
                    "Please complete all required fields."
            );

            doGet(request, response);
            return;
        }

        LocalDateTime eventDate = null;

        // Convert the form date into LocalDateTime.
        if (!isBlank(eventDateValue)) {

            try {

                eventDate =
                        LocalDateTime.parse(eventDateValue);

            } catch (DateTimeParseException e) {

                request.setAttribute(
                        "errorMessage",
                        "Please enter a valid date and time."
                );

                doGet(request, response);
                return;
            }
        }

        CommunityOpportunity opportunity =
                new CommunityOpportunity();

        opportunity.setUserId(userId);

        opportunity.setOpportunityType(
                opportunityType.trim()
        );

        opportunity.setTitle(
                title.trim()
        );

        opportunity.setDescription(
                description.trim()
        );

        opportunity.setLocation(
                cleanOptionalValue(location)
        );

        opportunity.setEventDate(eventDate);

        opportunity.setContactInformation(
                cleanOptionalValue(contactInformation)
        );

        CommunityOpportunityDAO opportunityDAO =
                new CommunityOpportunityDAO();

        boolean success =
                opportunityDAO.addOpportunity(opportunity);

        if (success) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/community-opportunities?success=1"
            );

        } else {

            request.setAttribute(
                    "errorMessage",
                    "Failed to publish the opportunity. Please try again."
            );

            doGet(request, response);
        }
    }

    /**
     * Checks whether a value is empty.
     */
    private boolean isBlank(String value) {

        return value == null
                || value.trim().isEmpty();
    }

    /**
     * Returns null for an empty optional value.
     */
    private String cleanOptionalValue(String value) {

        if (isBlank(value)) {
            return null;
        }

        return value.trim();
    }

    /**
     * Checks whether the selected type is allowed.
     */
    private boolean isValidOpportunityType(
            String opportunityType) {

        return "Project Collaboration".equals(opportunityType)
                || "Community Event".equals(opportunityType)
                || "Study Group".equals(opportunityType)
                || "Workshop".equals(opportunityType);
    }
}