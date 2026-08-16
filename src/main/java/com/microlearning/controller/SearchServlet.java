package com.microlearning.controller;

import com.microlearning.dao.CommunityPostDAO;
import com.microlearning.dao.ModuleDAO;
import com.microlearning.model.CommunityPost;
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
import java.util.ArrayList;

/**
 * Handles search requests from the search form.
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Read the keyword entered or selected by the user.
        String keyword = request.getParameter("q");

        HttpSession session = request.getSession(false);

        boolean loggedIn =
                session != null
                        && session.getAttribute("userId") != null;

        ArrayList<Module> moduleResults = new ArrayList<>();
        ArrayList<CommunityPost> postResults = new ArrayList<>();

        boolean hasSearched = false;

        // Store the keyword used for the database search.
        String searchKeyword = keyword;

        if (keyword != null && !keyword.trim().isEmpty()) {

            keyword = keyword.trim();
            searchKeyword = convertSuggestedKeyword(keyword);
            hasSearched = true;

            ModuleDAO moduleDAO = new ModuleDAO();
            CommunityPostDAO communityPostDAO =
                    new CommunityPostDAO();

            moduleResults =
                    moduleDAO.searchModules(searchKeyword);

            postResults =
                    communityPostDAO.searchPosts(searchKeyword);
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
                                response));

        // Show the original user-facing keyword on the page.
        context.setVariable("keyword", keyword);

        context.setVariable(
                "hasSearched",
                hasSearched);

        context.setVariable(
                "moduleResults",
                moduleResults);

        context.setVariable(
                "postResults",
                postResults);

        context.setVariable(
                "loggedIn",
                loggedIn);

        templateEngine.process(
                "search",
                context,
                response.getWriter());
    }

    /**
     * Converts suggested topics into
     * keywords that match existing platform content.
     */
    private String convertSuggestedKeyword(String keyword) {

        String normalisedKeyword =
                keyword.trim().toLowerCase();

        return switch (normalisedKeyword) {

            case "coding basics" ->
                    "Java";

            case "first java program",
                 "write your first program" ->
                    "Hello World";

            case "online safety",
                 "stay safe online" ->
                    "Cyber Security";

            case "community experiences",
                 "shared experiences" ->
                    "experience";

            case "projects and workshops",
                 "community opportunities" ->
                    "project";

            default ->
                    keyword;
        };
    }
}