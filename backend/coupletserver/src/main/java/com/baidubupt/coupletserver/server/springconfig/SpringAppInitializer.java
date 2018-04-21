package com.baidubupt.coupletserver.server.springconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * ref: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/WebApplicationInitializer.html
 */
public class SpringAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringAppInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        LOGGER.info("server onStartup");
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class } ;
    }

}