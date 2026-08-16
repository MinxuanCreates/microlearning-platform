package com.microlearning.controller;

import com.microlearning.dao.ModuleDAO;
import com.microlearning.dao.SharedKnowledgeDAO;
import com.microlearning.model.Module;
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
import java.util.ArrayList;

/**
 * Displays the microlearning module list and shared knowledge posts.
 */
@WebServlet("/modules")
public class ModuleServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        boolean loggedIn =
                session != null
                        && session.getAttribute("userId") != null;

        ModuleDAO moduleDAO = new ModuleDAO();

        ArrayList<Module> modules =
                moduleDAO.getAllModules();

        SharedKnowledgeDAO sharedKnowledgeDAO =
                new SharedKnowledgeDAO();

        ArrayList<SharedKnowledge> sharedKnowledgeList =
                sharedKnowledgeDAO.getAllSharedKnowledge();

        String knowledgeShared =
                request.getParameter("knowledgeShared");

        boolean knowledgeSharedSuccessfully =
                "true".equals(knowledgeShared);

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

        context.setVariable("modules", modules);

        context.setVariable(
                "sharedKnowledgeList",
                sharedKnowledgeList);

        context.setVariable("loggedIn", loggedIn);

        context.setVariable(
                "knowledgeSharedSuccessfully",
                knowledgeSharedSuccessfully);

        templateEngine.process(
                "modules",
                context,
                response.getWriter());
    }
}