# Run admin-module in background (PM2 or systemd)

## Option 1: PM2 (same approach as customer-module)

A sample **`ecosystem.config.js`** is in the project root; use it when starting PM2 from the adminmodule directory.

### On the server

```bash
# Install PM2 once (Node.js required)
npm install -g pm2

# Go to adminmodule (deploy path, e.g. /home/ubuntu/code/admin/adminmodule)
cd /path/to/adminmodule

# Build jar
mvn package -DskipTests

# Create logs dir
mkdir -p logs

# Start in background
pm2 start ecosystem.config.js
pm2 status
pm2 logs admin-module
```

### Persist across reboot

```bash
pm2 save
pm2 startup   # run the command it prints (e.g. sudo env PATH=... pm2 startup systemd -u ubuntu --hp /home/ubuntu)
```

### Useful commands

- `pm2 stop admin-module` / `pm2 restart admin-module`
- `pm2 delete admin-module` (remove from PM2)

### Optional: script that loads .env

```bash
./scripts/run-with-pm2.sh    # uses scripts/ecosystem.config.cjs for prod
./scripts/run-with-pm2.sh dev
```

---

## Option 2: systemd (existing setup)

Your repo already uses `systemctl start adminremit.service`. Ensure the unit exists on the server, e.g.:

**`/etc/systemd/system/adminremit.service`**
```ini
[Unit]
Description=Admin Module (XDC Payments)
After=network.target postgresql.service redis.service

[Service]
Type=simple
User=ubuntu
WorkingDirectory=/home/ubuntu/code/admin/adminmodule
EnvironmentFile=/home/ubuntu/code/admin/adminmodule/.env
ExecStart=/usr/bin/java -jar target/adminremit-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Then:
```bash
sudo systemctl daemon-reload
sudo systemctl start adminremit
sudo systemctl enable adminremit   # start on boot
sudo systemctl status adminremit
```

---

## Option 3: nohup (no extra tools)

```bash
cd /path/to/adminmodule
[ -f .env ] && set -a && source .env && set +a
nohup java -jar target/adminremit-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > logs/app.log 2>&1 &
echo $! > adminmodule.pid
# Stop later: kill $(cat adminmodule.pid)
```
