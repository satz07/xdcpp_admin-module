-- WEB3 Migration - Verify that Web2 and Web3 tables are isolated
-- Run this after making changes to verify the fix

-- 1. Count records in Web2 table
SELECT 'Web2 Table (admin_countries)' AS table_name, COUNT(*) AS record_count
FROM admin_countries
WHERE is_delete = false;

-- 2. Count records in Web3 table
SELECT 'Web3 Table (admin_countries_web3)' AS table_name, COUNT(*) AS record_count
FROM admin_countries_web3
WHERE is_delete = false;

-- 3. Check for duplicate country codes between tables (should be empty if isolated)
SELECT 
    'Duplicates between Web2 and Web3' AS check_type,
    w2.country_code,
    w2.country_name,
    w2.id AS web2_id,
    w3.id AS web3_id
FROM admin_countries w2
INNER JOIN admin_countries_web3 w3 
    ON w2.country_code = w3.country_code 
    AND w2.is_delete = false 
    AND w3.is_delete = false
ORDER BY w2.country_code;

-- 4. Show recent inserts in Web2 table (last 10)
SELECT 
    'Recent Web2 Inserts' AS info,
    id,
    country_code,
    country_name,
    create_at,
    create_by
FROM admin_countries
WHERE is_delete = false
ORDER BY create_at DESC
LIMIT 10;

-- 5. Show recent inserts in Web3 table (last 10)
SELECT 
    'Recent Web3 Inserts' AS info,
    id,
    country_code,
    country_name,
    create_at,
    create_by
FROM admin_countries_web3
WHERE is_delete = false
ORDER BY create_at DESC
LIMIT 10;

