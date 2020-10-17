# Technical description

## Project overview

The goal of the current project is to provide a service which is able to help its users to upload, manage and
gain insight about their purchase receipts.

## Server

Our server is located at `13.53.140.53`, hosted on AWS EC2. Services are running on 
[Docker](https://www.docker.com/) _*(planned, service is not yet deployed)_.

## Components

### Nginx

[Nginx](https://www.nginx.com/) reverse proxy published on port `8000` provides us with a single point of entry
to access our services. Client requests to the path `/api/*` are passed on to our backend service and all other
paths default to our frontend service.

### OCR _*(planned, not yet implemented)_

Our OCR service is a small containerized python application served by [Flask](https://palletsprojects.com/p/flask/)
framework internally on port `5000`. Its goal is to provide our backend service with text extraction features from
the image files uploaded by our users using [Tesseract](https://github.com/tesseract-ocr/), 
[Tensorflow](https://www.tensorflow.org/) and [PixelLib](https://github.com/ayoolaolafenwa/PixelLib). 

### Frontend

Our customer facing side is built on [Vue3](https://vuejs.org/) framework with some added looks by 
[Bulma](https://bulma.io/) CSS framework. The service is running on port `8008`. Requests to the backend are done
using [axios](https://www.npmjs.com/package/axios).

### Backend

Our backend service is providing a variety of endpoints for managing customer receipts and purchase entries connected
to them. It is also responsible for data validation and processing before saving it to persistent storage with 
[JPA](https://spring.io/projects/spring-data-jpa). It is built using
[Spring Boot](https://spring.io/projects/spring-boot) framework and running on Java 11, published internally on
port `8080`.

### Database

We are using the latest [PostgreSQL](https://registry.hub.docker.com/_/postgres) database container as our persistent
data storage published internally on port `5432`. Our models are stored in tables `customer`, `receipt` and `entry`
with relations shown on the diagram below.

![Diagram](datadiagram.png)
