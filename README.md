## Overview  

This is a simple Java REST API example using:
- Jersey
- JAX-RS
- MyBatis
- Guice
- Docker Compose
- Flyway

### Need to install locally  

- Gradle
- Docker
- Docker Compose

### How-to run  

#### Gradle:  

1. `gradle cleanEclipse eclipse` (if using Eclipse)
2. `gradle build`

#### Stand up external dependencies:  

1. Run `./local-deps.sh`
2. Verify that Wiremock, MySQL, and the Flyway migrate are running: `docker ps`
3. Give the Flyway migrate time to run (about 30 seconds - 1 minute); ensure the docker image is no longer around with `docker ps`. This ensures pristine seed data exists each run.

#### Bring up the webapp:  

1. Get an instance of Tomcat going (can configure this in Eclipse)
2. Setup Tomcat HTTP port for 8081
3. Mount the webapp module in Tomcat, using "/v1" as the root context
4. Launch the webapp (in Eclipse, can just right click the project and Run as --> Run on server)