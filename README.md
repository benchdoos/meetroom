# Meetroom 

## For HRs and PMs:

This project was developed as a test project for one of companies and it still being in development to show actual
skills and experience, that will help avoid time wasting on test tasks. You can see as code to analyse, as the project
up itself at [http://meetroom.herokuapp.com/](http://meetroom.herokuapp.com/) (wait a little bit while loading, until
project will boot up).

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

By default application start in DEVELOP (see `/resources/application.yml`) profile. It will create needed tables,
but will not create initial data.

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

You can create new account if needed. It will have role `ROLE_USER`.

Or you can use one of 3 pre-made user accounts:

| Login | Password | Role       |
| ----- | -------- | ---------- |
| admin | admin    | ROLE_ADMIN |
| user  | user     | ROLE_USER  |
| guest | guest    | ROLE_GUEST |

Admins can:

- create, edit, disable meeting rooms
- create, edit and delete **all** events
- act all management options

Users can:

- create events, edit, delete their own events
- manage their profile and other stuff

Guests can:

- see events


You can also create your own account, but be ready to set up an e-mail. There will be sent e-mail message to activate
account.
E-mail changes also need e-mail confirmation, as user password restores.



## Additional Information

Meeting rooms must have unique name and location.

Meeting events can not cross each other.

Event duration must be 30 minutes or more but less then 24 hours by default.

To override VM options:

```
-Dmeetroom.minimum-reservation-value
-Dmeetroom.maximum-reservation-value
```

## Some screenshots:

![Main page](https://i.postimg.cc/15qWz7v9/image.png)
![Meeting room page](https://i.postimg.cc/L5tFj4mT/image.png)
![Meeting info page with editing](https://i.postimg.cc/fTvhLb2W/image.png)
![User page](https://i.postimg.cc/jdtGGbsp/image.png)
![Update your avatar](https://i.postimg.cc/sXCn7Yxk/image.png)
![User events page](https://i.postimg.cc/QdpKVcL9/image.png)
![Management page](https://i.postimg.cc/T1zh33CJ/image.png)
![Manage users](https://i.postimg.cc/y6tDPLs3/image.png)

