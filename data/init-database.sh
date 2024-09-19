# Wait for SQL Server to start
sleep 20

# Run the SQL script to create the database, tables, and insert data
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Luucaohoang1604^^ -d master -i /usr/src/app/data/dataV2.sql
