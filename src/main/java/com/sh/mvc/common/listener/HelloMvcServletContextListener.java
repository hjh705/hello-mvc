package com.sh.mvc.common.listener;

import com.sh.mvc.common.HelloMvcUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class HelloMvcServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        HelloMvcUtils.servletContext = servletContext;
        System.out.println("HelloMvcUtils.servletContext를 설정했습니다. " + HelloMvcUtils.servletContext); // org.apache.catalina.core.ApplicationContextFacade@1186e48f
    }
}