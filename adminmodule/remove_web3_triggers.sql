-- WEB3 Migration - Remove any triggers that copy data between Web2 and Web3 tables
-- WARNING: Review the output of check_web3_triggers.sql before running this!
-- Only run this if you find triggers that are copying data between tables

-- Example: If you find a trigger named 'copy_to_web3_trigger', remove it like this:
-- DROP TRIGGER IF EXISTS copy_to_web3_trigger ON admin_countries;

-- Example: If you find a trigger named 'copy_to_web2_trigger', remove it like this:
-- DROP TRIGGER IF EXISTS copy_to_web2_trigger ON admin_countries_web3;

-- To list all triggers first, run:
-- SELECT trigger_name, event_object_table 
-- FROM information_schema.triggers 
-- WHERE event_object_table IN ('admin_countries', 'admin_countries_web3');

-- Then remove specific triggers:
-- DROP TRIGGER IF EXISTS <trigger_name> ON <table_name>;

-- Example for removing rules:
-- DROP RULE IF EXISTS <rule_name> ON <table_name>;

