# Meetroom 

## Introduction:

Meetroom gives ability to manage meeting rooms inside company,
gives ability to reserve room on custom period of time, create and edit events to each of meeting room.

## System requirements:

- Java 8
- Maven
- Postgre SQL

## Start application:

- Go to project root: `cd <project root folder>`
- Run maven: `mvn clean package`
- Run application: `java -jar target/meetroom-1.0.jar`

Before run is recommended to override application settings such as:

Database url: `-Dspring.datasource.url=<jdbc url to db>`

Database username: `-Dspring.datasource.username=<db username>`

Database password: `-Dspring.datasource.password=<db password>`

So application launch with overridden properties looks like:

```
-java -Dspring.datasource.url=jdbc:postgresql://<host>:<port>/<database_name> -Dspring.datasource.username=<db_username> -Dspring.datasource.password=<db_password> -jar target/meetroom-1.0.jar
```

 

There are 2 ways to run application:

## 1) DEVELOP PROFILE:

By default application start in DEVELOP (see `/resources/application.yml`) profile. It will create needed tables, but will not create initial data. 

You need to apply initial filling data sql script.

**SQL script** to create database location:

```
/src/main/resources/db/flyway/migration/V2__Fill_initial_data.sql
```

You need to override connection properties

## 2) PRODUCTION PROFILE:

Production profile will apply data migration scripts to fill all needed data filling database with initial information.

To start PRODUCTION profile, you need additionally to override:

Spring profile: `-Dspring.profiles.active=PRODUCTION`

Don't forget to override database connection settings.

## Usage:

Open link: [http://localhost:8080/meetroom/](http://localhost:8080/meetroom/)

To open secured pages you need to log in.

There are 2 pre-made user accounts:

| Login | Password | Role       |
| ----- | -------- | ---------- |
| admin | admin    | ROLE_ADMIN |
| user  | user     | ROLE_USER  |

