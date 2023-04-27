# Certificate system

REST API for Gift Certificates system on spring boot
(solving
this [task](https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%233.%20REST%20API%20Advanced/rest_api_advanced.md))

### 1.1 Build project
> - Run `./gradlew build` from the source folder.
>- Run `java -jar example.jar` with the generated jar file in gradle build directory.

### 1.2 Deploy project
Deploy project with custom configuration:
1. Prepare `.env` file in project directory, with environment variables. Like in example:

```text
DATABASE_USER={{database username}}
DATABASE_PASSWORD={{database username password}}
DATABASE_LINK={{database url}}
DATABASE_NAME={{database name}}
DATABASE_URL={{database url}}
DATABASE_PORT={{database port}}
```

Note that if you are trying to deploy it locally, you DO NOT need the database for application, because docker will
automatically create it, using configurations from `.env` file.

Also, you can find the `.env-example` file with the structure written above.

2. And then run command:

> `docker-compose --env-file .env up --build`

This will start the Spring Boot application and any other dependencies in the docker-compose.yml file. You can access
the application in `http://localhost:8080` url.
