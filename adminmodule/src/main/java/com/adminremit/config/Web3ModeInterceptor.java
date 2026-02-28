package com.adminremit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * WEB3 migration changes - Interceptor to ensure Web3 mode is set
 * before ALL controller methods execute (including REST endpoints)
 * 
 * This applies to:
 * - All HTTP methods (GET, POST, PUT, DELETE, PATCH)
 * - All @RequestMapping methods
 * - All REST endpoints (@GetMapping, @PostMapping, @DeleteMapping, etc.)
 * - All add, update, delete, and list operations
 */
@Component
public class Web3ModeInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(Web3ModeInterceptor.class);
    private static final String WEB3_MODE_SESSION_KEY = "web3Mode";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // Only process actual controller methods, not static resources or error handlers
            String requestURI = request.getRequestURI();
            if (requestURI == null || requestURI.equals("/error")) {
                return true;
            }
            
            HttpSession session = request.getSession(false);
            boolean isWeb3Mode = false;

            if (session != null) {
                Object sessionMode = session.getAttribute(WEB3_MODE_SESSION_KEY);
                if (sessionMode != null) {
                    isWeb3Mode = Boolean.parseBoolean(sessionMode.toString());
                }
            }

            // Ensure Web3 mode is set in ThreadLocal before ANY controller method executes
            // This applies to ALL operations: add, update, delete, list, etc.
            Web3ModeContext.setWeb3Mode(isWeb3Mode);
            
            if (isWeb3Mode) {
                logger.debug("WEB3 migration: Web3 mode set to true via Interceptor for {} {}", 
                    request.getMethod(), requestURI);
            }
        } catch (Exception e) {
            logger.error("WEB3 migration: Error setting Web3 mode in Interceptor", e);
            // Default to Web2 mode on error
            Web3ModeContext.setWeb3Mode(false);
        }
        
        return true; // Continue with the request
    }
}

