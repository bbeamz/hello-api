## Overview  

This is a simple Java REST API example using:
- __Jersey__: for simple Java REST webapp
- __JAX-RS__: for simple Java REST webapp
- __MyBatis__: for an easy MySQL DAO layer
- __Guice__: simple DI framework
- __Docker Compose__: for standing up external dependencies locally
- __Flyway__: for standing up a fresh local instance of MySQL with seed data

By default, this API has endpoints accessible at:
- GET /v1/hello/names
- GET /v1/hello/names/{id}
- POST /v1/hello/names `{ name: NEW_NAME }`
- DELETE /v1/hello/names/{name}
- GET /v1/hello/jobs/{name}

### Need to install locally  

- Gradle
- Docker
- Docker Compose

### How-to run  

For now, set the JRE of the project in your IDE to 1.8 / Java 8. (TODO: update dependencies for compatibility with newer Java runtimes)

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

### Helpful local things for testing

- After standing up MySQL with seed data, you can exec into the container with `docker exec -it java-rest-starter_mysql_1 mysql -uroot -phabana7` (these credentials are only for local or test development)
- After successfully launching the webapp, give it a test with:
   ```bash
   curl -X GET localhost:8081/v1/hello/names -v
   curl -H "Content-Type: application/json" -X POST -d '{"name":"sam"}' localhost:8081/v1/hello/names -v (extract the LOCATION header from the POST response
   curl -X GET localhost:8081/v1/hello/names/{ID_FROM_POST_LOCATION_HEADER} -v
   curl -X DELETE localhost:8081/v1/hello/names/sam -v` 
   ```
- You can verify mutations in the persitence layer by exec'ing into the local MySQL instance and verifying POST/DELETE operations modify the `hello` table correctly.
