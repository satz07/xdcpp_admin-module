package com.adminremit.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * WEB3 migration changes - Hibernate PhysicalNamingStrategy to dynamically resolve table names
 * based on Web2/Web3 mode and table mappings
 * Note: This class loads mappings directly since it's instantiated before Spring context
 */
public class Web3PhysicalNamingStrategy implements PhysicalNamingStrategy {

    private static final Logger logger = LoggerFactory.getLogger(Web3PhysicalNamingStrategy.class);
    private static final String MAPPING_FILE = "web3-table-mapping.properties";
    private static final Map<String, String> web2ToWeb3Mapping = new HashMap<>();
    
    static {
        loadTableMappings();
    }

    private static void loadTableMappings() {
        try {
            InputStream inputStream = Web3PhysicalNamingStrategy.class.getClassLoader()
                    .getResourceAsStream(MAPPING_FILE);
            if (inputStream == null) {
                logger.warn("WEB3 migration: Table mapping file {} not found. Web3 mode will use Web2 table names.", MAPPING_FILE);
                return;
            }

            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();

            for (String key : properties.stringPropertyNames()) {
                String web2Table = key.trim();
                String web3Table = properties.getProperty(key).trim();
                web2ToWeb3Mapping.put(web2Table, web3Table);
                logger.info("WEB3 migration: Mapped {} -> {}", web2Table, web3Table);
            }

            logger.info("WEB3 migration: Loaded {} table mappings in PhysicalNamingStrategy", web2ToWeb3Mapping.size());
        } catch (IOException e) {
            logger.error("WEB3 migration: Error loading table mappings from {}", MAPPING_FILE, e);
        }
    }

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        if (name == null) {
            return name;
        }

        String tableName = name.getText();
        
        // Check if Web3 mode is enabled
        boolean isWeb3Mode = Web3ModeContext.isWeb3Mode();
        
        // Always log to help debug - use info level for visibility
        logger.info("WEB3 migration: NamingStrategy called for table: {}, Web3 mode: {}", tableName, isWeb3Mode);
        
        // Web2 mode: always use original table name (no changes)
        if (!isWeb3Mode) {
            logger.debug("WEB3 migration: Web2 mode - Using original table name: {}", tableName);
            return name;
        }

        // Web3 mode: 
        // 1. First check if there's a custom mapping in properties file (takes precedence)
        String web3TableName = web2ToWeb3Mapping.get(tableName);
        if (web3TableName != null && !web3TableName.isEmpty()) {
            logger.info("WEB3 migration: Using custom mapped Web3 table {} for Web2 table {}", web3TableName, tableName);
            return Identifier.toIdentifier(web3TableName, name.isQuoted());
        }

        // 2. If no custom mapping, automatically append _web3 suffix to all tables
        String autoWeb3TableName = tableName + "_web3";
        logger.info("WEB3 migration: Auto-appending _web3 suffix: {} -> {} (Web3 mode: {})", tableName, autoWeb3TableName, isWeb3Mode);
        return Identifier.toIdentifier(autoWeb3TableName, name.isQuoted());
        
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }
}

