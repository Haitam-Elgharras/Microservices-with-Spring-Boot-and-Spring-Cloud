# Build Docker Image:
- Configured the Docker image name and pull policy in pom.xml:
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <image>
                        <name>your_dockerhub_username/microservices-${project.artifactId}:${project.version}</name>
                    </image>
                    <pullPolicy>IF_NOT_PRESENT</pullPolicy>
                </configuration>
            </plugin>
        </plugins>
    </build>

- change the data source URL in the application.properties file to use the Docker container name:
  spring.datasource.url=jdbc:mysql://host.docker.internal:3306/your_db_name
  we add host.docker.internal meaning that the mysql server is on the host machine
  
- Ran the following command in the folder containing the pom.xml to build the Docker image:
    mvn spring-boot:build-image -DskipTests

- to check the state of services:
    docker-compose ps

- The EUREKA_CLIENT_SERVICEURL_DEFAULTZONE property is not picked up automatically.
  There is a permanent issue with camelcasing which creates an inconsistency.
  EUREKA_CLIENT_SERVICEURL_DEFAULTZONE is not converted to eureka.client.serviceUrl.defaultZone. 
  It's converted to eureka.client.serviceurl.defaultzone. 
  a solution is to use `eureka.client.serviceUrl.defaultZone`

### note
- the environment inside the docker compose file override the app props
- the call for the proxy service will work because we are using feign, but the call with the rest template will not work because we are hardcoding local host instead we can write currency-exchange or use a variable
