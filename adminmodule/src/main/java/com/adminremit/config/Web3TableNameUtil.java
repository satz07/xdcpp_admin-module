package com.adminremit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * WEB3 migration changes - Utility to resolve physical table names
 * for native SQL queries based on Web2/Web3 mode.
 *
 * This mirrors the behavior of Web3PhysicalNamingStrategy so that
 * native SQL does not hard-code Web2 table names.
 */
public final class Web3TableNameUtil {

    private static final Logger logger = LoggerFactory.getLogger(Web3TableNameUtil.class);
    private static final String MAPPING_FILE = "web3-table-mapping.properties";
    private static final Map<String, String> web2ToWeb3Mapping = new HashMap<>();

    static {
        loadTableMappings();
    }

    private Web3TableNameUtil() {
        // Utility class
    }

    private static void loadTableMappings() {
        try {
            InputStream inputStream = Web3TableNameUtil.class.getClassLoader()
                    .getResourceAsStream(MAPPING_FILE);
            if (inputStream == null) {
                logger.warn("WEB3 migration: Table mapping file {} not found for Web3TableNameUtil.", MAPPING_FILE);
                return;
            }

            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();

            for (String key : properties.stringPropertyNames()) {
                String web2Table = key.trim();
                String web3Table = properties.getProperty(key).trim();
                web2ToWeb3Mapping.put(web2Table, web3Table);
                logger.info("WEB3 migration: (Util) Mapped {} -> {}", web2Table, web3Table);
            }

            logger.info("WEB3 migration: (Util) Loaded {} table mappings", web2ToWeb3Mapping.size());
        } catch (IOException e) {
            logger.error("WEB3 migration: Error loading table mappings from {} in Web3TableNameUtil", MAPPING_FILE, e);
        }
    }

    /**
     * Resolve the physical table name based on current Web3 mode.
     *
     * @param baseTableName Web2 table name (e.g., "admin_countries")
     * @return Resolved physical table name based on mode and mapping
     */
    public static String resolveTableName(String baseTableName) {
        boolean isWeb3Mode = Web3ModeContext.isWeb3Mode();

        if (!isWeb3Mode) {
            return baseTableName; // Web2 mode: use original table
        }

        // Web3 mode: first check for custom mapping
        String web3TableName = web2ToWeb3Mapping.get(baseTableName);
        if (web3TableName != null && !web3TableName.isEmpty()) {
            logger.debug("WEB3 migration: (Util) Using custom mapped Web3 table {} for {}", web3TableName, baseTableName);
            return web3TableName;
        }

        // If no custom mapping, append _web3
        String autoWeb3TableName = baseTableName + "_web3";
        logger.debug("WEB3 migration: (Util) Auto-appending _web3 suffix: {} -> {}", baseTableName, autoWeb3TableName);
        return autoWeb3TableName;
    }
}


