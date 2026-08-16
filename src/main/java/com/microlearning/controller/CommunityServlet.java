package com.microlearning.controller;

import com.microlearning.dao.CommunityCommentDAO;
import com.microlearning.dao.CommunityPostDAO;
import com.microlearning.model.CommunityPost;
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
 * Displays the community page and handles new community posts.
 * Visitors can view posts and comments.
 * Only logged-in users can create posts and comments.
 */
@WebServlet("/community")
public class CommunityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        boolean loggedIn =
                session != null &&
                        session.getAttribute("userId") != null;

        CommunityPostDAO postDAO = new CommunityPostDAO();
        CommunityCommentDAO commentDAO = new CommunityCommentDAO();

        ArrayList<CommunityPost> posts = postDAO.getAllPosts();

        // Loads the comments belonging to each post
        for (CommunityPost post : posts) {
            post.setComments(
                    commentDAO.getCommentsByPostId(post.getPostId())
            );
        }

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

        context.setVariable("posts", posts);
        context.setVariable("loggedIn", loggedIn);

        if (loggedIn) {
            context.setVariable(
                    "username",
                    session.getAttribute("username")
            );
        }

        templateEngine.process(
                "community",
                context,
                response.getWriter()
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // Only logged-in users can create posts
        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        int userId =
                (int) session.getAttribute("userId");

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        CommunityPost post =
                new CommunityPost(userId, title, content);

        CommunityPostDAO postDAO =
                new CommunityPostDAO();

        boolean success =
                postDAO.addPost(post);

        if (success) {
            response.sendRedirect(
                    request.getContextPath() + "/community"
            );
            return;
        }

        CommunityCommentDAO commentDAO =
                new CommunityCommentDAO();

        ArrayList<CommunityPost> posts =
                postDAO.getAllPosts();

        // Loads comments again if creating the post fails
        for (CommunityPost existingPost : posts) {
            existingPost.setComments(
                    commentDAO.getCommentsByPostId(
                            existingPost.getPostId()
                    )
            );
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

        WebContext context = new WebContext(
                application.buildExchange(request, response),
                request.getLocale()
        );

        context.setVariable(
                "error",
                "Failed to create post. Please try again."
        );

        context.setVariable("posts", posts);
        context.setVariable("loggedIn", true);
        context.setVariable(
                "username",
                session.getAttribute("username")
        );

        templateEngine.process(
                "community",
                context,
                response.getWriter()
        );
    }
}