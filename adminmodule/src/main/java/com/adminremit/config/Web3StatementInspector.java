package com.adminremit.config;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
    
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * WEB3 migration changes - Hibernate StatementInspector to dynamically modify SQL queries
 * at runtime to use Web3 table names when Web3 mode is enabled.
 * 
 * This works by intercepting SQL before execution and replacing table names.
 * This is necessary because PhysicalNamingStrategy is only called during metadata building,
 * not dynamically for each query.
 */
public class Web3StatementInspector implements StatementInspector {

        private static final Logger logger = LoggerFactory.getLogger(Web3StatementInspector.class);
    private static final String MAPPING_FILE = "web3-table-mapping.properties";
    private static final Map<String, String> web2ToWeb3Mapping = new HashMap<>();
    
    // Pattern to match table names in SQL (handles FROM, JOIN, UPDATE, INSERT INTO, etc.)
    // Matches: admin_countries, admin_currencies, admin_users, payment_recevie_mode_master, etc.
    // WEB3 migration changes - Also matches tables ending with _master (like payment_recevie_mode_master)
    // Note: This will also match audit tables (admin_countries_aud), but we exclude them in the replacement logic
    private static final Pattern TABLE_NAME_PATTERN = Pattern.compile(
        "\\b((?:admin_[a-z_]+)|(?:[a-z_]+_master))\\b", 
        Pattern.CASE_INSENSITIVE
    );
    
    static {
        loadTableMappings();
    }
    
    private static void loadTableMappings() {
        try {
            InputStream inputStream = Web3StatementInspector.class.getClassLoader()
                    .getResourceAsStream(MAPPING_FILE);
            if (inputStream == null) {
                logger.warn("WEB3 migration: Table mapping file {} not found in StatementInspector.", MAPPING_FILE);
                return;
            }

            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();

            for (String key : properties.stringPropertyNames()) {
                String web2Table = key.trim();
                String web3Table = properties.getProperty(key).trim();
                web2ToWeb3Mapping.put(web2Table.toLowerCase(), web3Table);
                logger.info("WEB3 migration: StatementInspector mapped {} -> {}", web2Table, web3Table);
            }

            logger.info("WEB3 migration: Loaded {} table mappings in StatementInspector", web2ToWeb3Mapping.size());
        } catch (IOException e) {
            logger.error("WEB3 migration: Error loading table mappings in StatementInspector", e);
        }
    }

    @Override
    public String inspect(String sql) {
        if (sql == null || sql.isEmpty()) {
            return sql;
        }

        // Check if Web3 mode is enabled
        boolean isWeb3Mode = Web3ModeContext.isWeb3Mode();
        
        if (!isWeb3Mode) {
            // Web2 mode: return SQL as-is
            return sql;
        }

        // Web3 mode: replace table names with _web3 suffix
        String modifiedSql = sql;
        
        // Use regex to find and replace all admin_* table names
        // WEB3 migration changes - Using Java 8 compatible approach (Matcher.appendReplacement)
        // instead of Java 9+ replaceAll(Function) for compatibility
        Matcher matcher = TABLE_NAME_PATTERN.matcher(modifiedSql);
        StringBuffer sb = new StringBuffer();
        boolean found = false;
        
        while (matcher.find()) {
            found = true;
            String tableName = matcher.group(1);
            String tableNameLower = tableName.toLowerCase();
            
            String replacement = tableName; // Default: no change
            
            // WEB3 migration changes - Skip audit tables (Hibernate Envers creates _aud tables)
            // Audit tables should remain as-is (e.g., admin_countries_aud should not become admin_countries_aud_web3)
            // Also skip tables that already have _web3 suffix to avoid double-processing
            if (!tableNameLower.endsWith("_aud") && !tableNameLower.endsWith("_web3")) {
                // Check for custom mapping first
                String web3TableName = web2ToWeb3Mapping.get(tableNameLower);
                if (web3TableName == null || web3TableName.isEmpty()) {
                    // No custom mapping: auto-append _web3
                    web3TableName = tableName + "_web3";
                }
                replacement = web3TableName;
                logger.debug("WEB3 migration: StatementInspector replacing {} with {}", tableName, web3TableName);
            } else {
                logger.debug("WEB3 migration: StatementInspector skipping table (audit or already Web3): {}", tableName);
            }
            
            // Escape special characters in replacement for appendReplacement
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        
        if (found) {
            matcher.appendTail(sb);
            modifiedSql = sb.toString();
        }

        if (!sql.equals(modifiedSql)) {
            logger.info("WEB3 migration: StatementInspector modified SQL - Table names updated from Web2 to Web3");
            logger.debug("WEB3 migration: Original SQL: {}", sql);
            logger.debug("WEB3 migration: Modified SQL: {}", modifiedSql);
        }

        return modifiedSql;
    }
}

