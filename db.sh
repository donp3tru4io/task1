#!/bin/bash
sudo apt-get install mssql-server
sudo systemctl start mssql-server
sudo MSSQL_PID=Developer ACCEPT_EULA=Y MSSQL_SA_PASSWORD=QWerTY123 /opt/mssql/bin/mssql-conf -n setup
