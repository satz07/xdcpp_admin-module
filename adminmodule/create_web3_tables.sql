-- WEB3 Migration - Create Web3 tables if they don't exist
-- This script creates admin_countries_web3 table with the same structure as admin_countries

-- Check if table exists, if not create it
DO $$
BEGIN
    -- Create admin_countries_web3 table if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables 
                   WHERE table_schema = 'public' 
                   AND table_name = 'admin_countries_web3') THEN
        
        CREATE TABLE admin_countries_web3 (
            id BIGSERIAL PRIMARY KEY,
            country_name VARCHAR(255) NOT NULL,
            country_code VARCHAR(20) NOT NULL,
            dialing_code INTEGER,
            default_language VARCHAR(255),
            iso_code VARCHAR(255),
            is_enabled BOOLEAN,
            is_restricted BOOLEAN DEFAULT false,
            publish BOOLEAN DEFAULT true,
            create_at TIMESTAMP,
            update_at TIMESTAMP,
            create_by VARCHAR(255),
            updated_by VARCHAR(255),
            is_delete BOOLEAN DEFAULT false
        );
        
        -- Create indexes similar to admin_countries
        CREATE INDEX IF NOT EXISTS idx_countries_web3_country_code ON admin_countries_web3(country_code);
        CREATE INDEX IF NOT EXISTS idx_countries_web3_is_delete ON admin_countries_web3(is_delete);
        CREATE INDEX IF NOT EXISTS idx_countries_web3_publish ON admin_countries_web3(publish);
        
        RAISE NOTICE 'Table admin_countries_web3 created successfully';
    ELSE
        RAISE NOTICE 'Table admin_countries_web3 already exists';
    END IF;
END $$;

-- Verify table structure
SELECT 
    'admin_countries_web3' AS table_name,
    column_name,
    data_type,
    is_nullable
FROM information_schema.columns
WHERE table_name = 'admin_countries_web3'
ORDER BY ordinal_position;

