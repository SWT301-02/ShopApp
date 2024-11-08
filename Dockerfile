FROM mcr.microsoft.com/mssql/server:2017-latest
ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=Luucaohoang1604^^
ENV MSSQL_PID=Developer
ENV MSSQL_TCP_PORT=1433
WORKDIR /src
COPY ./data/dataV3.sql ./dataV3.sql
CMD (/opt/mssql/bin/sqlservr --accept-eula & ) & \
     sleep 30 && \
     /opt/mssql-tools/bin/sqlcmd -S127.0.0.1 -Usa -PLuucaohoang1604^^ -i ./dataV3.sql

EXPOSE 1433