/**
 * PM2 config for admin-module (Spring Boot).
 * Uses java -jar directly, same style as customer-module.
 *
 * On server (from adminmodule dir):
 *   mkdir -p logs
 *   pm2 start ecosystem.config.js              # normal auto-restart
 *   # or:
 *   pm2 start ecosystem.config.js --no-autorestart
 *   pm2 save && pm2 startup
 */
const path = require('path');

module.exports = {
  apps: [{
    name: 'admin-module',
    script: 'java',
    args: '-jar target/adminremit-0.0.1-SNAPSHOT.jar',
    cwd: path.join(__dirname),
    interpreter: 'none',
    instances: 1,
    watch: false,
    autorestart: true,
    max_memory_restart: '1500M',
    kill_timeout: 60000,
    error_file: 'logs/err.log',
    out_file: 'logs/out.log',
    merge_logs: true,
  }],
};

