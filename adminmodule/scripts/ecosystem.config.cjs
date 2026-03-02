/**
 * PM2 ecosystem file for admin-module (Spring Boot).
 * Usage on server:
 *   cd /path/to/adminmodule
 *   pm2 start scripts/ecosystem.config.cjs
 *   pm2 save && pm2 startup   # optional: restart on reboot
 */
module.exports = {
  apps: [
    {
      name: 'admin-module',
      script: 'java',
      args: [
        '-jar', 'target/adminremit-0.0.1-SNAPSHOT.jar',
        '--spring.profiles.active=prod',
      ],
      cwd: __dirname + '/..',
      instances: 1,
      autorestart: true,
      watch: false,
      max_memory_restart: '1G',
      error_file: 'logs/admin-module-err.log',
      out_file: 'logs/admin-module-out.log',
      merge_logs: true,
      time: true,
    },
  ],
};
