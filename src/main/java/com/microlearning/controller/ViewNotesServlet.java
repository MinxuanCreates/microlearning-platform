package com.microlearning.controller;

import com.microlearning.dao.NoteDAO;
import com.microlearning.model.Note;
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
 * Displays notes created by the logged-in learner.
 */
@WebServlet("/view-notes")
public class ViewNotesServlet extends HttpServlet {

    /**
     * Displays all notes created by the current user.
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        // Only logged-in users can view notes.
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        // Get the user's notes and linked module details.
        NoteDAO noteDAO = new NoteDAO();
        ArrayList<Note> notes = noteDAO.getNotesByUserId(userId);

        // Check whether a note was just saved.
        boolean noteSaved =
                "true".equals(request.getParameter("noteSaved"));

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        TemplateEngine templateEngine =
                ThymeleafUtil.getTemplateEngine(getServletContext());

        JakartaServletWebApplication application =
                JakartaServletWebApplication.buildApplication(
                        getServletContext()
                );

        WebContext context = new WebContext(
                application.buildExchange(request, response),
                request.getLocale()
        );

        // Send page data to view-notes.html.
        context.setVariable("notes", notes);
        context.setVariable("noteSaved", noteSaved);
        context.setVariable("loggedIn", true);

        templateEngine.process(
                "view-notes",
                context,
                response.getWriter()
        );
    }
}