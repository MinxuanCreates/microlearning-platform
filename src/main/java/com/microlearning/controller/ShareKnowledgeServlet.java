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
 * Displays the shared knowledge form
 * and handles the submission of new knowledge posts.
 */
@WebServlet("/share-knowledge")
public class ShareKnowledgeServlet extends HttpServlet {

    /**
     * Displays the share knowledge form.
     * Visitors who are not logged in are redirected to the login page.
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        renderPage(
                request,
                response,
                null);
    }

    /**
     * Saves a new shared knowledge post.
     */
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        int userId =
                (Integer) session.getAttribute("userId");

        String title =
                cleanValue(request.getParameter("title"));

        String category =
                cleanValue(request.getParameter("category"));

        String experienceType =
                cleanValue(request.getParameter("experienceType"));

        String description =
                cleanValue(request.getParameter("description"));

        String content =
                cleanValue(request.getParameter("content"));

        String keyTakeaway =
                cleanValue(request.getParameter("keyTakeaway"));

        String readingTime =
                cleanValue(request.getParameter("readingTime"));

        if (title.isEmpty()
                || category.isEmpty()
                || experienceType.isEmpty()
                || description.isEmpty()
                || content.isEmpty()
                || readingTime.isEmpty()) {

            renderPage(
                    request,
                    response,
                    "Please complete all required fields.");

            return;
        }

        SharedKnowledge knowledge =
                new SharedKnowledge();

        knowledge.setUserId(userId);
        knowledge.setTitle(title);
        knowledge.setCategory(category);
        knowledge.setExperienceType(experienceType);
        knowledge.setDescription(description);
        knowledge.setContent(content);
        knowledge.setKeyTakeaway(keyTakeaway);
        knowledge.setReadingTime(readingTime);

        /*
         * reserved for possible
         * image and video support in future development.
         */
        knowledge.setImageUrl(null);
        knowledge.setVideoUrl(null);

        SharedKnowledgeDAO sharedKnowledgeDAO =
                new SharedKnowledgeDAO();

        boolean saved =
                sharedKnowledgeDAO.addSharedKnowledge(knowledge);

        if (saved) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/modules?knowledgeShared=true");

        } else {

            renderPage(
                    request,
                    response,
                    "Your knowledge post could not be saved. "
                            + "Please try again.");
        }
    }

    /**
     * Displays the share knowledge page.
     */
    private void renderPage(
            HttpServletRequest request,
            HttpServletResponse response,
            String error)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

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

        context.setVariable("loggedIn", true);
        context.setVariable("error", error);

        templateEngine.process(
                "share-knowledge",
                context,
                response.getWriter());
    }

    /**
     * Removes unnecessary spaces
     * and prevents null values.
     */
    private String cleanValue(String value) {

        if (value == null) {
            return "";
        }

        return value.trim();
    }
}