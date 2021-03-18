# Perfil TIC Ecommerce API
##### _Installation - Database:_
1. Configure a MariaDB database server properly (10.5.8 or higher)
2. Run the script "ptec.sql" and then script "inserts.sql"

##### _Installation - Spring Boot:_
1. Clone PT_Ecommerce API from provided repository. 
2. Set ip, port, user and password on application.yaml file in order to connect to MariaDB server 
   (must use the same user that executed scripts on previous part)   
3. Open a terminal and go to "PT_Ecommerce" directory 
4. Build project with maven (i.e. mvn clean install)
5. Start application:
  `java -jar .\target\perfiltic.ecommerce.api-1.0.0.jar`
6. Then, you can test endpoints listed on URL: "http://localhost:9080/PTEC-API/swagger-ui". 
 


###### _NOTE_:
ENJOY!!
