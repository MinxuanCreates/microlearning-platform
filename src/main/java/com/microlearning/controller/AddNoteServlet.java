package com.microlearning.controller;

import com.microlearning.dao.ModuleDAO;
import com.microlearning.dao.NoteDAO;
import com.microlearning.model.Module;
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

/**
 * Displays the note form and saves new learner notes.
 */
@WebServlet("/add-note")
public class AddNoteServlet extends HttpServlet {

    /**
     * Displays the add-note form.
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        // Only logged-in users can create notes.
        if (!isLoggedIn(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Integer moduleId = getModuleId(request);
        Module module = getModule(moduleId);

        renderAddNotePage(
                request,
                response,
                moduleId,
                module,
                null,
                null,
                null
        );
    }

    /**
     * Validates and saves a new note.
     */
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // Only logged-in users can save notes.
        if (!isLoggedIn(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        Integer moduleId = getModuleId(request);
        Module module = getModule(moduleId);

        // Remove unnecessary spaces.
        title = title == null ? "" : title.trim();
        content = content == null ? "" : content.trim();

        // Check required fields.
        if (title.isEmpty() || content.isEmpty()) {

            renderAddNotePage(
                    request,
                    response,
                    moduleId,
                    module,
                    title,
                    content,
                    "Please enter both a note title and note content."
            );

            return;
        }

        // Match the limits used in the HTML form.
        if (title.length() > 150) {

            renderAddNotePage(
                    request,
                    response,
                    moduleId,
                    module,
                    title,
                    content,
                    "The note title must be 150 characters or fewer."
            );

            return;
        }

        if (content.length() > 2000) {

            renderAddNotePage(
                    request,
                    response,
                    moduleId,
                    module,
                    title,
                    content,
                    "The note content must be 2,000 characters or fewer."
            );

            return;
        }

        Note note = new Note(userId, moduleId, title, content);

        NoteDAO noteDAO = new NoteDAO();
        boolean success = noteDAO.addNote(note);

        if (success) {

            /*
             * Return to the current lesson when the note was created
             * from a lesson page.
             */
            if (moduleId != null && module != null) {
                response.sendRedirect(
                        request.getContextPath()
                                + "/lesson?moduleId="
                                + moduleId
                                + "&noteSaved=true"
                );
            } else {
                response.sendRedirect(
                        request.getContextPath()
                                + "/view-notes?noteSaved=true"
                );
            }

            return;
        }

        // Show the form again if saving fails.
        renderAddNotePage(
                request,
                response,
                moduleId,
                module,
                title,
                content,
                "Failed to save the note. Please try again."
        );
    }

    /**
     * Checks whether the user is logged in.
     */
    private boolean isLoggedIn(HttpSession session) {
        return session != null
                && session.getAttribute("userId") != null;
    }

    /**
     * Reads the module ID from the request.
     */
    private Integer getModuleId(HttpServletRequest request) {

        String moduleIdParam = request.getParameter("moduleId");

        if (moduleIdParam == null || moduleIdParam.isBlank()) {
            return null;
        }

        try {
            int moduleId = Integer.parseInt(moduleIdParam);

            if (moduleId <= 0) {
                return null;
            }

            return moduleId;

        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Finds the selected module.
     */
    private Module getModule(Integer moduleId) {

        if (moduleId == null) {
            return null;
        }

        ModuleDAO moduleDAO = new ModuleDAO();
        return moduleDAO.getModuleById(moduleId);
    }

    /**
     * Displays the add-note page with its form values.
     */
    private void renderAddNotePage(
            HttpServletRequest request,
            HttpServletResponse response,
            Integer moduleId,
            Module module,
            String title,
            String content,
            String error)
            throws IOException {

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

        context.setVariable("moduleId", moduleId);
        context.setVariable("module", module);
        context.setVariable("title", title);
        context.setVariable("content", content);
        context.setVariable("error", error);
        context.setVariable("loggedIn", true);

        templateEngine.process(
                "add-note",
                context,
                response.getWriter()
        );
    }
}