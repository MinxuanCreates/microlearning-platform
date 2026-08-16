package com.microlearning.controller;

import com.microlearning.dao.LearningProgressDAO;
import com.microlearning.dao.ModuleDAO;
import com.microlearning.model.Module;
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
 * Displays one lesson based on the selected module.
 */
@WebServlet("/lesson")
public class LessonServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        // Set the response format.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Read the selected module ID.
        String moduleIdParam = request.getParameter("moduleId");

        if (moduleIdParam == null || moduleIdParam.isBlank()) {
            redirectToModules(request, response);
            return;
        }

        Module module;

        try {
            int moduleId = Integer.parseInt(moduleIdParam);

            ModuleDAO moduleDAO = new ModuleDAO();
            module = moduleDAO.getModuleById(moduleId);

        } catch (NumberFormatException e) {
            redirectToModules(request, response);
            return;
        }

        // Return to the module list if the lesson does not exist.
        if (module == null) {
            redirectToModules(request, response);
            return;
        }

        // Check whether the user is logged in.
        HttpSession session = request.getSession(false);

        boolean loggedIn =
                session != null
                        && session.getAttribute("userId") != null;

        boolean moduleCompleted = false;

        // Check the user's lesson progress.
        if (loggedIn) {
            int userId = (int) session.getAttribute("userId");

            LearningProgressDAO progressDAO =
                    new LearningProgressDAO();

            moduleCompleted =
                    progressDAO.isModuleCompleted(
                            userId,
                            module.getModuleId()
                    );
        }

        // Check whether a note was just saved.
        boolean noteSaved =
                "true".equals(request.getParameter("noteSaved"));

        // Prepare Thymeleaf.
        TemplateEngine templateEngine =
                ThymeleafUtil.getTemplateEngine(getServletContext());

        JakartaServletWebApplication application =
                JakartaServletWebApplication.buildApplication(
                        getServletContext()
                );

        WebContext context =
                new WebContext(
                        application.buildExchange(request, response),
                        request.getLocale()
                );

        // Send lesson data to the page.
        context.setVariable("module", module);
        context.setVariable("loggedIn", loggedIn);
        context.setVariable("moduleCompleted", moduleCompleted);
        context.setVariable("noteSaved", noteSaved);

        // Add a useful link based on the module category.
        if ("Cyber Security".equalsIgnoreCase(module.getCategory())) {

            context.setVariable(
                    "usefulLinkText",
                    "NCSC: Top Tips for Staying Secure Online"
            );

            context.setVariable(
                    "usefulLinkUrl",
                    "https://www.ncsc.gov.uk/collection/"
                            + "top-tips-for-staying-secure-online"
            );

        } else {

            context.setVariable(
                    "usefulLinkText",
                    "W3Schools: What is Programming?"
            );

            context.setVariable(
                    "usefulLinkUrl",
                    "https://www.w3schools.com/whatis/"
            );
        }

        // Display the lesson page.
        templateEngine.process(
                "lesson",
                context,
                response.getWriter()
        );
    }

    /**
     * Redirects the user to the module list.
     */
    private void redirectToModules(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.sendRedirect(
                request.getContextPath() + "/modules"
        );
    }
}