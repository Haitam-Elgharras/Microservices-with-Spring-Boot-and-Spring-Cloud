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
