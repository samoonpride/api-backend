# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.1/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Docker Compose Support](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#features.docker-compose)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Docker Compose support

This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* mysql: [`mysql:latest`](https://hub.docker.com/_/mysql)

Please review the tags of the used images and set them to the same as you're running in production.

### How to Run the Application

This application is containerized using Docker and orchestrated with Docker Compose. To run the application, follow
these steps:

1. Navigate to `src/main/resources`. Make a copy of `application.properties.example` and rename it to `application.properties`. Fill the value of each necessary configurations.
   - For `spring.datasource.url`, it should be something similar to this pattern: `jdbc:mysql://{host}:{port}/{databaseName}`, e.g. `jdbc:mysql://localhost:3306/samoonpride`.
   - For `jwt.key`, you have to generate any 256-bit keys and fill it in (you can generate it from any sources such as [this one](https://acte.ltd/utils/randomkeygen)). This is the secret key used for generating JWT tokens and validating JWT signatures. It should be kept as a secret. If one is exposed, generating a new one must be strictly done.
2. Ensure Docker and Docker Compose are installed on your machine.
3. Navigate to the project directory that contains the `docker-compose.yaml` file.
4. Run the command to start the application
    ```bash
   docker-compose up -d
   ```

Please note that the application will start on the default port 8080.
