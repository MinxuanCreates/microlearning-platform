package com.microlearning.controller;

import com.microlearning.dao.CommunityCommentDAO;
import com.microlearning.model.CommunityComment;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Handles new comments added to community posts.
 */
@WebServlet("/add-community-comment")
public class AddCommunityCommentServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // Only logged-in users can add comments
        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        String postIdValue = request.getParameter("postId");
        String commentContent =
                request.getParameter("commentContent");

        // Return to the community page if the post ID is missing
        if (postIdValue == null) {
            response.sendRedirect(
                    request.getContextPath() + "/community"
            );
            return;
        }

        // Return to the post details page if the comment is empty
        if (commentContent == null ||
                commentContent.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/community-post?postId="
                            + postIdValue
                            + "#comments"
            );
            return;
        }

        try {
            int postId = Integer.parseInt(postIdValue);
            int userId =
                    (int) session.getAttribute("userId");

            CommunityComment comment =
                    new CommunityComment();

            comment.setPostId(postId);
            comment.setUserId(userId);
            comment.setCommentContent(
                    commentContent.trim()
            );

            CommunityCommentDAO commentDAO =
                    new CommunityCommentDAO();

            commentDAO.addComment(comment);

            // Return to the comment section of the same post
            response.sendRedirect(
                    request.getContextPath()
                            + "/community-post?postId="
                            + postId
                            + "#comments"
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() + "/community"
            );
        }
    }
}