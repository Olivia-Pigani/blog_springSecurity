# About

This project is dockerized, development mode and production mode are available in the Dokerfile.

## Set up  


### .env file

You have to add at the same location of the "docker-compose.yml" file : a ".env" file, which contain :
```
BLOG_SPRING_PORT=<?>
DB_PORT=<?>
DB_HOST=<?>
DB_SCHEMA=<?>

DB_USER=<?>
DB_PASS=<?>

POSTGRES_USER=postgres_user
POSTGRES_PASSWORD=<?>
POSTGRES_DB=<?>

LOCALHOST_PGADMIN_PORT=<?>
PGADMIN_DEFAULT_PASSWORD=<?>
PGADMIN_DEFAULT_EMAIL=<?>
PGADMIN_PORT=80

ADMIN_EMAIL=<?>
ADMIN_PASSWORD=<?>

SECRET_KEY=<?>

```

### Pgadmin

Use the email and password you've set with PGADMIN_EMAIL and PGADMIN_PASSWORD to log into it, then, right-click on 'Servers' and select 'Create' > 'Server',
go to the 'General' and give your server a name, then switch to the 'Connection' tab :

1. Hostname/address:  refer to the service name of your PostgreSQL container, so "postgres".
2. Port: refer to DB_PORT
3. Maintenance database: refer to POSTGRES_DB
4. Username: refer to POSTGRES_USER.
5. Password:refer to POSTGRES_PASSWORD.

Click 'Save'.