-- Check if admin_countries_web3 table exists and its structure
SELECT 
    table_name,
    table_type
FROM information_schema.tables
WHERE table_schema = 'public'
    AND table_name IN ('admin_countries', 'admin_countries_web3')
ORDER BY table_name;

-- Check table structure if it exists
SELECT 
    column_name,
    data_type,
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'admin_countries_web3'
ORDER BY ordinal_position;

-- Check if table has any rows at all (including deleted)
SELECT 
    'admin_countries_web3' AS table_name,
    COUNT(*) AS total_rows,
    COUNT(CASE WHEN is_delete = false THEN 1 END) AS active_rows,
    COUNT(CASE WHEN is_delete = true THEN 1 END) AS deleted_rows
FROM admin_countries_web3;

