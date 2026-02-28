package com.adminremit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * WEB3 migration changes - Controller advice to ensure Web3 mode is set
 * before ALL controller methods execute (including add, update, delete operations),
 * ensuring consistent table name resolution across all operations
 * 
 * This applies to:
 * - All @RequestMapping methods (GET, POST, PUT, DELETE)
 * - All REST endpoints (@GetMapping, @PostMapping, @DeleteMapping, etc.)
 * - All controller methods that perform database operations
 */
@ControllerAdvice
@Order(1) // Ensure this runs early
public class Web3ModeControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(Web3ModeControllerAdvice.class);
    private static final String WEB3_MODE_SESSION_KEY = "web3Mode";

    /**
     * This method is called before EVERY @RequestMapping method in ALL controllers
     * It ensures Web3 mode is set from session before any database operation
     */
    @ModelAttribute
    public void setWeb3Mode(HttpServletRequest request) {
        try {
            // Skip error pages to reduce logging noise
            String requestURI = request.getRequestURI();
            if (requestURI != null && requestURI.equals("/error")) {
                return;
            }
            
            HttpSession session = request.getSession(false);
            boolean isWeb3Mode = false;

            if (session != null) {
                Object sessionMode = session.getAttribute(WEB3_MODE_SESSION_KEY);
                if (sessionMode != null) {
                    isWeb3Mode = Boolean.parseBoolean(sessionMode.toString());
                }
            }

            // Ensure Web3 mode is set in ThreadLocal before any controller method executes
            // This applies to ALL operations: add, update, delete, list, etc.
            Web3ModeContext.setWeb3Mode(isWeb3Mode);
            
            if (isWeb3Mode) {
                logger.debug("WEB3 migration: Web3 mode set to true via ControllerAdvice for {} {}", 
                    request.getMethod(), requestURI);
            }
        } catch (Exception e) {
            logger.error("WEB3 migration: Error setting Web3 mode in ControllerAdvice", e);
            // Default to Web2 mode on error
            Web3ModeContext.setWeb3Mode(false);
        }
    }
}

