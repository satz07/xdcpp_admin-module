-- WEB3 Migration - Check countries in both Web2 and Web3 tables
-- Run these commands in PostgreSQL to see the data in both tables

-- 1. Check Web2 table (admin_countries) - Active countries only
SELECT 
    'Web2 Table (admin_countries)' AS table_name,
    id,
    country_code,
    country_name,
    dialing_code,
    iso_code,
    is_delete,
    publish,
    create_at,
    create_by
FROM admin_countries
WHERE is_delete = false
ORDER BY country_name;

-- 2. Check Web3 table (admin_countries_web3) - Active countries only
SELECT 
    'Web3 Table (admin_countries_web3)' AS table_name,
    id,
    country_code,
    country_name,
    dialing_code,
    iso_code,
    is_delete,
    publish,
    create_at,
    create_by
FROM admin_countries_web3
WHERE is_delete = false
ORDER BY country_name;

-- 3. Count comparison
SELECT 
    'Web2 (admin_countries)' AS table_name,
    COUNT(*) AS total_count,
    COUNT(CASE WHEN is_delete = false THEN 1 END) AS active_count,
    COUNT(CASE WHEN is_delete = true THEN 1 END) AS deleted_count
FROM admin_countries
UNION ALL
SELECT 
    'Web3 (admin_countries_web3)' AS table_name,
    COUNT(*) AS total_count,
    COUNT(CASE WHEN is_delete = false THEN 1 END) AS active_count,
    COUNT(CASE WHEN is_delete = true THEN 1 END) AS deleted_count
FROM admin_countries_web3;

-- 4. Find countries that exist in Web2 but not in Web3
SELECT 
    'Only in Web2' AS location,
    w2.id,
    w2.country_code,
    w2.country_name,
    w2.is_delete
FROM admin_countries w2
LEFT JOIN admin_countries_web3 w3 
    ON w2.country_code = w3.country_code 
    AND w2.is_delete = false
WHERE w3.id IS NULL
    AND w2.is_delete = false
ORDER BY w2.country_name;

-- 5. Find countries that exist in Web3 but not in Web2
SELECT 
    'Only in Web3' AS location,
    w3.id,
    w3.country_code,
    w3.country_name,
    w3.is_delete
FROM admin_countries_web3 w3
LEFT JOIN admin_countries w2 
    ON w3.country_code = w2.country_code 
    AND w3.is_delete = false
WHERE w2.id IS NULL
    AND w3.is_delete = false
ORDER BY w3.country_name;

-- 6. Find countries that exist in BOTH tables (duplicates)
SELECT 
    'In Both Tables' AS location,
    w2.id AS web2_id,
    w3.id AS web3_id,
    w2.country_code,
    w2.country_name,
    w2.is_delete AS web2_deleted,
    w3.is_delete AS web3_deleted
FROM admin_countries w2
INNER JOIN admin_countries_web3 w3 
    ON w2.country_code = w3.country_code
WHERE w2.is_delete = false 
    AND w3.is_delete = false
ORDER BY w2.country_name;

-- 7. Show recently created countries (last 10 in each table)
SELECT 
    'Recent Web2' AS source,
    id,
    country_code,
    country_name,
    create_at,
    create_by
FROM admin_countries
WHERE is_delete = false
ORDER BY create_at DESC
LIMIT 10
UNION ALL
SELECT 
    'Recent Web3' AS source,
    id,
    country_code,
    country_name,
    create_at,
    create_by
FROM admin_countries_web3
WHERE is_delete = false
ORDER BY create_at DESC
LIMIT 10;

-- 8. Check deleted countries in both tables
SELECT 
    'Deleted in Web2' AS location,
    id,
    country_code,
    country_name,
    is_delete,
    update_at
FROM admin_countries
WHERE is_delete = true
ORDER BY update_at DESC;

SELECT 
    'Deleted in Web3' AS location,
    id,
    country_code,
    country_name,
    is_delete,
    update_at
FROM admin_countries_web3
WHERE is_delete = true
ORDER BY update_at DESC;

