package com.adminremit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * WEB3 migration changes - Controller to handle Web3 mode toggle from frontend
 */
@RestController
@RequestMapping("/api/web3")
public class Web3ModeController {

    private static final Logger logger = LoggerFactory.getLogger(Web3ModeController.class);
    private static final String WEB3_MODE_SESSION_KEY = "web3Mode";
    private static final String MAPPING_FILE = "web3-table-mapping.properties";
    private static final Map<String, String> web2ToWeb3Mapping = new HashMap<>();
    
    static {
        loadTableMappings();
    }
    
    private static void loadTableMappings() {
        try {
            InputStream inputStream = Web3ModeController.class.getClassLoader()
                    .getResourceAsStream(MAPPING_FILE);
            if (inputStream == null) {
                logger.warn("WEB3 migration: Table mapping file {} not found.", MAPPING_FILE);
                return;
            }

            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();

            for (String key : properties.stringPropertyNames()) {
                String web2Table = key.trim();
                String web3Table = properties.getProperty(key).trim();
                web2ToWeb3Mapping.put(web2Table, web3Table);
            }

            logger.info("WEB3 migration: Loaded {} table mappings in Web3ModeController", web2ToWeb3Mapping.size());
        } catch (IOException e) {
            logger.error("WEB3 migration: Error loading table mappings from {}", MAPPING_FILE, e);
        }
    }

    /**
     * Set Web3 mode in session
     * WEB3 migration changes - Accepts form data from frontend
     */
    @PostMapping("/mode")
    public Map<String, Object> setWeb3Mode(
            @RequestParam(value = "enabled", required = false, defaultValue = "false") boolean enabled,
            HttpSession session) {
        
        session.setAttribute(WEB3_MODE_SESSION_KEY, enabled);
        Web3ModeContext.setWeb3Mode(enabled);
        
        logger.info("WEB3 migration: Web3 mode set to {} for session", enabled);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("web3Mode", enabled);
        return response;
    }

    /**
     * Get current Web3 mode from session
     */
    @GetMapping("/mode")
    public Map<String, Object> getWeb3Mode(HttpSession session) {
        Object mode = session.getAttribute(WEB3_MODE_SESSION_KEY);
        boolean isWeb3Mode = mode != null && Boolean.parseBoolean(mode.toString());
        
        Map<String, Object> response = new HashMap<>();
        response.put("web3Mode", isWeb3Mode);
        return response;
    }

    /**
     * Get resolved table name for a given entity table name
     * WEB3 migration changes - Returns the actual table name being used based on Web2/Web3 mode
     */
    @GetMapping("/table-name")
    public Map<String, Object> getResolvedTableName(
            @RequestParam(value = "tableName", required = true) String tableName,
            HttpSession session) {
        
        Object mode = session.getAttribute(WEB3_MODE_SESSION_KEY);
        boolean isWeb3Mode = mode != null && Boolean.parseBoolean(mode.toString());
        
        // Use the same logic as Web3PhysicalNamingStrategy
        String resolvedTableName = resolveTableName(tableName, isWeb3Mode);
        
        Map<String, Object> response = new HashMap<>();
        response.put("originalTableName", tableName);
        response.put("resolvedTableName", resolvedTableName);
        response.put("web3Mode", isWeb3Mode);
        response.put("isMapped", !tableName.equals(resolvedTableName));
        
        return response;
    }

    /**
     * Resolve table name based on Web3 mode (same logic as Web3PhysicalNamingStrategy)
     */
    private String resolveTableName(String tableName, boolean isWeb3Mode) {
        if (!isWeb3Mode) {
            return tableName; // Web2 mode: use original table name
        }

        // Web3 mode: 
        // 1. First check if there's a custom mapping in properties file (takes precedence)
        String web3TableName = web2ToWeb3Mapping.get(tableName);
        if (web3TableName != null && !web3TableName.isEmpty()) {
            return web3TableName;
        }

        // 2. If no custom mapping, automatically append _web3 suffix
        return tableName + "_web3";
    }
}

