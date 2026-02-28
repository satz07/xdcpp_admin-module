package com.adminremit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * WEB3 migration changes - Filter to set Web3 mode from request/session
 * Reads Web3 mode from session or request parameter and sets it in ThreadLocal
 */
@Component
@Order(1)
public class Web3ModeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(Web3ModeFilter.class);
    private static final String WEB3_MODE_PARAM = "web3Mode";
    private static final String WEB3_MODE_SESSION_KEY = "web3Mode";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestURI = httpRequest.getRequestURI();
            
            // Skip static resources to reduce noise and improve performance
            if (isStaticResource(requestURI)) {
                chain.doFilter(request, response);
                return;
            }
            
            HttpSession session = httpRequest.getSession(false);
            
            boolean isWeb3Mode = false;
            
            // Check request parameter first (for API calls or direct requests)
            String web3ModeParam = httpRequest.getParameter(WEB3_MODE_PARAM);
            if (web3ModeParam != null) {
                isWeb3Mode = Boolean.parseBoolean(web3ModeParam);
            } else if (session != null) {
                // Check session attribute (set by frontend toggle)
                Object sessionMode = session.getAttribute(WEB3_MODE_SESSION_KEY);
                if (sessionMode != null) {
                    isWeb3Mode = Boolean.parseBoolean(sessionMode.toString());
                }
            }
            
            // Set Web3 mode in ThreadLocal for Hibernate to use
            Web3ModeContext.setWeb3Mode(isWeb3Mode);
            
            if (isWeb3Mode) {
                logger.debug("WEB3 migration: Web3 mode enabled for request {}", requestURI);
            }
            
            chain.doFilter(request, response);
        } finally {
            // Clean up ThreadLocal after request
            Web3ModeContext.clear();
        }
    }
    
    /**
     * Check if the request URI is a static resource that doesn't need Web3 mode processing
     */
    private boolean isStaticResource(String uri) {
        if (uri == null) {
            return false;
        }
        return uri.startsWith("/css/") ||
               uri.startsWith("/js/") ||
               uri.startsWith("/images/") ||
               uri.startsWith("/img/") ||
               uri.startsWith("/libs/") ||
               uri.startsWith("/webjars/") ||
               uri.startsWith("/.well-known/") ||
               uri.endsWith(".css") ||
               uri.endsWith(".js") ||
               uri.endsWith(".png") ||
               uri.endsWith(".jpg") ||
               uri.endsWith(".jpeg") ||
               uri.endsWith(".gif") ||
               uri.endsWith(".ico") ||
               uri.endsWith(".svg") ||
               uri.endsWith(".woff") ||
               uri.endsWith(".woff2") ||
               uri.endsWith(".ttf") ||
               uri.endsWith(".eot") ||
               uri.endsWith(".map");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("WEB3 migration: Web3ModeFilter initialized");
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}

