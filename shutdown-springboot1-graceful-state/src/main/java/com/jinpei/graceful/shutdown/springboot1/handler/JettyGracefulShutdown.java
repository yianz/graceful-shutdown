package com.jinpei.graceful.shutdown.springboot1.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * Jetty web容器优雅停机
 * Created by liuzhaoming on 2020/12/21
 */
@Slf4j
public class JettyGracefulShutdown implements ApplicationListener<ContextClosedEvent> {

    private final EmbeddedWebApplicationContext context;

    public JettyGracefulShutdown(EmbeddedWebApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        EmbeddedServletContainer servletContainer = context.getEmbeddedServletContainer();
        if (servletContainer instanceof JettyEmbeddedServletContainer) {
            log.info("Start to stop jetty servlet container.");
            JettyEmbeddedServletContainer jettyContainer = (JettyEmbeddedServletContainer) servletContainer;
            try {
                jettyContainer.getServer().stop();
            } catch (Exception e) {
                log.warn("Fait to stop jetty thread pool ", e);
            }
        }
    }
}
