![Banner](docs/official_banner.jpg)

# Project overview

A service for uploading the images of your purchase receipts to keep track of your shopping behavior. 

## The Team
- **Team Lead and Project Manager**:  Jaak Kütt  @jakutt
- **Lead Backend Developer**:  Kristjan Kõiv  @krikoi
- **Lead Frontend Developer**:  Kristajn Koitla  @krkoit
- **DevOps Engineer**:  Mart Kaasik  @markaa

## Project requirements

- Java 11 or newer
- Docker
- docker-compose
- npm

## Running the service locally

- Start the database and Nginx proxy for frontend with `sudo docker-compose up -d`
    - *if it doesn't work you might need to restart your docker service with* `sudo systemctl restart docker`
- Run the application via `Application.main` 

#### Frontend
- setup the project with `npm install`
- compile and hot-reload for development with `npm run serve-proxy`
- compile for production with `npm run build`
- then you should be able to open the page up in your browser with the link given by npm
- run frontend tests with `npm run test:unit`

#### Nginx proxy
- proxy redirect traffic from `localhost:8000/api/*` to backend and `localhost:8000/*` to frontend


## Swagger

- [api-docs](http://localhost:8080/v2/api-docs)
- [swagger-ui](http://localhost:8080/swagger-ui/)

## Docs
 
- [Database](docs/database.md)
- [Lombok](docs/lombok.md)
- [Server](docs/aws_server.md)
- [Techical](docs/technical.md)
