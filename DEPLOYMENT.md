# Deployment Guide for Railway

Your application failed to deploy because Docker Compose configurations (like environment variables) are not automatically transferred to Railway. You must manually configure these settings in the Railway dashboard.

## 1. Database Setup

You need a PostgreSQL database.
1.  In your Railway project, add a new **PostgreSQL** service.
2.  Once created, go to the "Variables" tab of the PostgreSQL service to find your connection details (Host, Port, User, Password, Database).
3.  Alternatively, use the "Connect" tab to copy the `JDBC URL` if available, or construct it yourself:
    `jdbc:postgresql://<HOST>:<PORT>/<DATABASE>`

## 2. Service Configurations

You need to configure Environment Variables for each service in Railway.

### for `ass1-clothes-warehouse` (App Service)

Go to the **Variables** tab for your App service and add:

| Variable Name | Value | Description |
|--------------|-------|-------------|
| `DB_URL` | `jdbc:postgresql://<DB_HOST>:<DB_PORT>/<DB_NAME>` | Connection string to your PostgreSQL database. Ensure it starts with `jdbc:postgresql://`. |
| `DB_USERNAME` | `<DB_USER>` | Database username (e.g., `postgres`). |
| `DB_PASSWORD` | `<DB_PASSWORD>` | Database password. |
| `DISTRIBUTION_CENTRE_URL` | `https://<DISTRIBUTION_APP_URL>/api` | URL of your deployed Distribution Manager service. |
| `PORT` | `8080` (or leave defined by Railway) | Railway sets this automatically. |

### for `distribution-manager` Service

Go to the **Variables** tab for your Distribution Manager service and add:

| Variable Name | Value | Description |
|--------------|-------|-------------|
| `DB_URL` | `jdbc:postgresql://<DB_HOST>:<DB_PORT>/<DB_NAME>` | Connection string to your PostgreSQL database. |
| `DB_USERNAME` | `<DB_USER>` | Database username (e.g., `postgres`). |
| `DB_PASSWORD` | `<DB_PASSWORD>` | Database password. |
| `MANAGER_USERNAME` | `admin` (or your choice) | Username for manager access. |
| `MANAGER_PASSWORD` | `password` (or your choice) | Password for manager access. |
| `PORT` | `8081` (or leave defined by Railway) | Railway sets this automatically. |

## 3. Database Initialization

The `init.sql` file in your repository is not automatically executed on Railway's managed database.
- You may need to manually run the SQL command `CREATE DATABASE distribution_centre;` if your database doesn't exist.
- Since `spring.jpa.hibernate.ddl-auto=update` is set, tables will be created automatically when the app connects successfully.

## 4. Troubleshooting

- **CrashLoopBackOff / Connection Refused**: This means the app cannot connect to the database. Check your `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` variables.
- **Port Errors**: Ensure your app listens on `0.0.0.0` and the port defined by `$PORT`. We updated `application.properties` to handle this.
