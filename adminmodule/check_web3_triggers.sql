-- WEB3 Migration - Check for triggers, rules, or functions that might copy data between Web2 and Web3 tables
-- Run this in your PostgreSQL database to diagnose why data appears in both tables

-- 1. Check for triggers on admin_countries table
SELECT 
    trigger_name,
    event_manipulation,
    event_object_table,
    action_statement,
    action_timing
FROM information_schema.triggers
WHERE event_object_table IN ('admin_countries', 'admin_countries_web3')
ORDER BY event_object_table, trigger_name;

-- 2. Check for rules on admin_countries table
SELECT 
    schemaname,
    tablename,
    rulename,
    definition
FROM pg_rules
WHERE tablename IN ('admin_countries', 'admin_countries_web3')
ORDER BY tablename, rulename;

-- 3. Check for foreign key constraints that might reference both tables
SELECT
    tc.table_name, 
    kcu.column_name, 
    ccu.table_name AS foreign_table_name,
    ccu.column_name AS foreign_column_name,
    tc.constraint_name
FROM information_schema.table_constraints AS tc 
JOIN information_schema.key_column_usage AS kcu
    ON tc.constraint_name = kcu.constraint_name
    AND tc.table_schema = kcu.table_schema
JOIN information_schema.constraint_column_usage AS ccu
    ON ccu.constraint_name = tc.constraint_name
    AND ccu.table_schema = tc.table_schema
WHERE tc.constraint_type = 'FOREIGN KEY'
    AND (tc.table_name LIKE '%countries%' OR ccu.table_name LIKE '%countries%')
ORDER BY tc.table_name;

-- 4. Check for functions that might insert into both tables
SELECT 
    p.proname AS function_name,
    pg_get_functiondef(p.oid) AS function_definition
FROM pg_proc p
JOIN pg_namespace n ON p.pronamespace = n.oid
WHERE n.nspname = 'public'
    AND pg_get_functiondef(p.oid) LIKE '%admin_countries%'
ORDER BY p.proname;

-- 5. Check if admin_countries_web3 table exists and its structure
SELECT 
    table_name,
    column_name,
    data_type,
    is_nullable
FROM information_schema.columns
WHERE table_name IN ('admin_countries', 'admin_countries_web3')
ORDER BY table_name, ordinal_position;

-- 6. Check for any materialized views or views that might combine both tables
SELECT 
    table_name,
    view_definition
FROM information_schema.views
WHERE view_definition LIKE '%admin_countries%'
ORDER BY table_name;

