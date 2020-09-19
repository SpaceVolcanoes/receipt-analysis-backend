# Team 02 Spacevolcanoes Backend

## Project requirements

- Java 11
- Docker
- docker-compose

## Running the service locally

- Start the database with `docker-compose up -d` (safe to run even if the db is already up)
- Run the application via `HeroesBackendApplication.main` 

## Swagger

http://localhost:8080/v2/api-docs

http://localhost:8080/swagger-ui/

## Working with a local Database

### Connecting to your database with IntelliJ

- Make sure the db and service is running (see above)
- Make sure you have Intellij ultimate or education edition (as community doesn't have database connection tab)
- Open the `database` tab on the right side of your screen
- Add new connection via `new > Data Source > PostgreSQL`
- Add a name for your database (I'm using `analysis-local` in my examples below)
- Leave the host and port to defaults `localhost` and `5432`
- Use user/pass/database `postgres`, `development` and `analysis`
- Under `Schemas` tab check the `All databases` box
- You will now see the database tables at `analysis-local > databases > schemas > public > tables`

### Want to delete all the data in your db and start fresh?

If you want to do it once, just run the following (the command in the middle will physically delete the db files)
```
docker-compose down && docker volume rm team-02-spacevolcanoes-backend_analysis-db-data && docker-compose up -d
```

If you're actively developing the models and trying out new things, I'd suggest adding `-Pspring.jpa.hibernate.ddl-auto=create-drop` under
`Run > Edit Configurations > HeroesBackendApplication > Program Argument`.
This will drop the tables every time you stop your application and create a fresh setup on the next start.

### Read the database logs

```
docker logs -f analysis-db
```

### Stop the database

```
docker-compose down
```
