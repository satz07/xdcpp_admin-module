#!/bin/bash
# Quick script to check countries in Web2 and Web3 tables
# Usage: ./quick_check_countries.sh [database_name] [username]

DB_NAME=${1:-"remittances-qa-xdcpp"}
DB_USER=${2:-"postgres"}

echo "=========================================="
echo "Checking Countries in Web2 and Web3 Tables"
echo "Database: $DB_NAME"
echo "=========================================="
echo ""

echo "1. Web2 Table - Active Countries:"
psql -U $DB_USER -d $DB_NAME -c "SELECT id, country_code, country_name, is_delete FROM admin_countries WHERE is_delete = false ORDER BY country_name;"

echo ""
echo "2. Web3 Table - Active Countries:"
psql -U $DB_USER -d $DB_NAME -c "SELECT id, country_code, country_name, is_delete FROM admin_countries_web3 WHERE is_delete = false ORDER BY country_name;"

echo ""
echo "3. Count Comparison:"
psql -U $DB_USER -d $DB_NAME -c "SELECT 'Web2' AS table_name, COUNT(*) AS active_count FROM admin_countries WHERE is_delete = false UNION ALL SELECT 'Web3' AS table_name, COUNT(*) AS active_count FROM admin_countries_web3 WHERE is_delete = false;"

echo ""
echo "4. Countries in BOTH tables:"
psql -U $DB_USER -d $DB_NAME -c "SELECT w2.country_code, w2.country_name, w2.id AS web2_id, w3.id AS web3_id FROM admin_countries w2 INNER JOIN admin_countries_web3 w3 ON w2.country_code = w3.country_code WHERE w2.is_delete = false AND w3.is_delete = false ORDER BY w2.country_name;"

