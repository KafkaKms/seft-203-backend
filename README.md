# SEFT-203

## Prerequisite

* Docker (https://docs.docker.com/get-docker)
* docker-compose (https://docs.docker.com/compose/install)

## How to run

> docker-compose up --build

Then, go to documentation url

> http://localhost:8080/swagger-ui.html

## Login API

User/pass: admin/Admin@123

## Build (for Java only)

Change "11.0.10.j9-adpt" to your java version if you use sdk

> sdk use java 11.0.10.j9-adpt

> ./gradlew build

For skipping tests (one test intentionally to fail)

> ./gradlew build -x test

## Running tests

> ./gradlew test

## Things Done
- [ ] Auth API
- [x] Contact API
- [ ] Dashboard API
- [ ] Report API
- [x] Task API
- [x] Data stored in the RDBMS
- [x] Migration management
- [x] Errors should be handled
- [x] Logs are store in file and print to the console
- [x] Return HTTP code correctly
- [x] Data need to be validated
- [ ] All the APIs only allow authenticated users to access except /auth/login API
- [ ] Password needs to be stored securely in the database
- [ ] Unit test
- [ ] Code coverage 70%

