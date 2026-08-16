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

/**
 * Displays the detail page for one community post.
 */
@WebServlet("/community-post")
public class CommunityPostDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        boolean loggedIn =
                session != null &&
                        session.getAttribute("userId") != null;

        String postIdParam = request.getParameter("postId");

        CommunityPost post = null;

        if (postIdParam != null) {
            try {
                int postId = Integer.parseInt(postIdParam);

                CommunityPostDAO communityPostDAO =
                        new CommunityPostDAO();

                post = communityPostDAO.getPostById(postId);

                if (post != null) {
                    CommunityCommentDAO commentDAO =
                            new CommunityCommentDAO();

                    post.setComments(
                            commentDAO.getCommentsByPostId(postId)
                    );
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

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

        context.setVariable("post", post);
        context.setVariable("loggedIn", loggedIn);

        if (loggedIn) {
            context.setVariable(
                    "username",
                    session.getAttribute("username")
            );
        }

        templateEngine.process(
                "community-post",
                context,
                response.getWriter()
        );
    }
}