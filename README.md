# Vaadin Gradle Skeleton Starter Spring Boot

This project demos the possibility of having Vaadin project in npm+webpack mode using Gradle.
Please see the [Starting a Vaadin project using Gradle](https://vaadin.com/docs/latest/guide/start/gradle) for the documentation.


Prerequisites:
* Java 21 or higher
* node.js and npm. Vaadin Gradle plugin will install those for you
  automatically (handy for CI), or you can install it to your OS:
  * Windows: [node.js Download site](https://nodejs.org/en/download/) - use the .msi 64-bit installer
  * Linux: `sudo apt install npm`
* Git
* (Optionally): Intellij Community

## Vaadin Versions

* The [v14](https://github.com/vaadin/base-starter-spring-gradle) branch (the default one)
  contains the example app for Vaadin 14
* The [master](https://github.com/vaadin/base-starter-spring-gradle/tree/master) branch
  contains the example app for Vaadin 19

## Running With Spring Boot via Gradle In Development Mode

Run the following command in this repo:

```bash
./gradlew clean bootRun
```

Run with UI hot deploy
```bash
./gradlew bootRun --args='--vaadin.frontend.hotdeploy=true'
```
or set `vaadin.frontend.hotdeploy=true` in `application.properties`


Now you can open the [http://localhost:8080](http://localhost:8080) with your browser.

## Running With Spring Boot from your IDE In Development Mode

Run the following command in this repo, to create necessary Vaadin config files:

```bash
./gradlew clean vaadinPrepareFrontend
```

The `build/vaadin-generated/` folder will now contain proper configuration files.

Open the `DemoApplication` class, and Run/Debug its main method from your IDE.

Now you can open the [http://localhost:8080](http://localhost:8080) with your browser.

## Building In Production Mode

Run the following command in this repo:

```bash
./gradlew clean build -Pvaadin.productionMode
```

That will build this app in production mode as a runnable jar archive; please find the jar file
in `build/libs/base-starter-spring-gradle*.jar`.
You can run the JAR file with:

```bash
cd build/libs/
java -jar base-starter-spring-gradle*.jar
```

```bash
docker run --rm -v .:/app -v ~/.gradle:/root/.gradle --entrypoint /bin/sh eclipse-temurin:21-jdk \
-c "cd /app && chmod +x gradlew && ./gradlew && ./gradlew clean && ./gradlew build -x test -Pvaadin.productionMode"

docker run --rm -v .:/app -v ~/.gradle:/root/.gradle --entrypoint /bin/sh gradle:8.4-jdk21-jammy \
-c "cd /app && chmod +x gradle && gradle clean build -x test -Pvaadin.productionMode"


docker build -t librebuy .
docker run -p 8080:8080 librebuy
docker run --rm --name librebuy --network postgres-pgadmin_default --network-alias=librebuy --env DB_HOST=local_pgdb -p 8080:8080 librebuy
docker run --rm --name librebuy --network postgres-pgadmin_default --network-alias=librebuy --env DB_HOST=local_pgdb -p 8443:8443 librebuy

docker run -d --restart always --name librebuy --network postgres-pgadmin_default --network-alias=librebuy --env DB_HOST=local_pgdb -p 8443:8443 librebuy

```

Now you can open the [http://localhost:8080](http://localhost:8080) with your browser.

### Building In Production On CI

Usually the CI images will not have node.js+npm available. However, Vaadin Gradle Plugin will download it for you
automatically, there is no need for you to do anything.
To build your app for production in CI, just run:

```bash
./gradlew clean build -Pvaadin.productionMode
```
