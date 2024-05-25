Project is part of Information Systems university course at FER (Zagreb). It is a Spring application with Next.js frontend and MSSQL database.
Main purpose of application is to manage one real estate agency and show some implementation best practices learnt at university.
Functionalities: - managing of real estate owners and their data (personal data and real estates which they own)
                 - managing real estates of owners
                 - managing real estate types (for example: house, apartment, garage, ...)

To start this application you need to have docker installed on your machine, Java 21, Node version 20 or higher.
First step is to run docker image of database located in DZ03/server/nis/utils. To run this docker image type in your terminal: docker-compose up.
After this position yourself in DZ03/server/nis and type the following command to start Spring application from terminal: mvn spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.password=A_Str0ng_Required_Password --spring.datasource.url=jdbc:sqlserver://localhost:1433;database=NIS;encrypt=false --spring.datasource.username=sa"
This will run application and setup required database connection environment variables. Running an application can be done more easily from your desired IDE as well.
To run frontend part locate yourself in DZ03/client and type npm install. This will install all required Node modules. After this step type npm run dev in terminal, open browser at: http://localhost:3000 and enjoy. 
