package com.microlearning.util;

import jakarta.servlet.ServletContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

/**
 * Creates the Thymeleaf engine for loading HTML templates.
 */
public class ThymeleafUtil {

    private static TemplateEngine templateEngine;

    public static TemplateEngine getTemplateEngine(ServletContext servletContext) {
        if (templateEngine == null) {

            JakartaServletWebApplication application =
                    JakartaServletWebApplication.buildApplication(servletContext);

            WebApplicationTemplateResolver templateResolver =
                    new WebApplicationTemplateResolver(application);

            templateResolver.setPrefix("/WEB-INF/templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode("HTML");
            templateResolver.setCharacterEncoding("UTF-8");

            templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);
        }

        return templateEngine;
    }
}