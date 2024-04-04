This project is dockerized, dev and prod mode are available in the spring Dokerfile.

pgadmin : 

Log into pgAdmin: Use the email and password you've set with PGADMIN_DEFAULT_EMAIL and PGADMIN_DEFAULT_PASSWORD to log into the pgAdmin web interface.

Add a New Server in pgAdmin:

Please make 
In the pgAdmin dashboard, right-click on 'Servers' in the left sidebar and select 'Create' > 'Server'.
In the 'Create Server' dialog, go to the 'General' tab and give your server a meaningful name.
Switch to the 'Connection' tab.
Configure the Server Connection:

Hostname/address: Enter postgres, which is the service name of your PostgreSQL container. Docker's internal networking allows services within the same network to communicate using their service names as hostnames.
Port: Use the default PostgreSQL port 5432, unless you've configured it differently.
Maintenance database: Use the database name you've specified in POSTGRES_DB.
Username: Enter the username you've set in POSTGRES_USER.
Password: Enter the password you've set in POSTGRES_PASSWORD.
You might want to check the 'Save password' option for convenience.
Click 'Save' to finish setting up the server connection.