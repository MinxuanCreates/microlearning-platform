# i+1 Microlearning Platform

This repository contains the source code and database for the i+1 microlearning platform developed as part of my MSc research project.

i+1 is a microlearning platform designed to support women learning computing through short courses, shared knowledge and meaningful community connections. The name is inspired by the idea of i = i + 1. It represents small steps towards growth, support and empowerment.

## Main Features

- User registration and login
- Short learning modules
- Lesson videos, subtitles and transcripts
- Search and Ask Me Here
- Personal notes
- My Journey progress tracking
- Community posts, comments and opportunities
- Shared Knowledge

## Technologies

- Java 21
- Jakarta Servlet API 6.0.0
- Thymeleaf 3.1.5
- MySQL 8.0.35
- MySQL Connector/J 9.7.0
- Maven
- Apache Tomcat 10.1.39
- Bootstrap 5.3.3
- HTML, CSS and JavaScript
- JDBC

## Project Structure

- `src/` – source code and web resources
- `database/` – database SQL file
- `pom.xml` – Maven configuration

## Database Setup

1. Create a MySQL database named `microlearning_db`.
2. Import `database/microlearning_db.sql`.
3. Check the database connection settings in the application.

The database contains dummy test data used for the prototype.

## Running the Project

1. Download the repository.
2. Open the project in IntelliJ IDEA.
3. Make sure Java 21 is available.
4. Make sure the MySQL database setup is complete.
5. Configure Apache Tomcat 10.1.39 in IntelliJ IDEA.
6. Run the project with the Tomcat configuration.
7. Open `http://localhost:8080/microlearning-platform/` in a web browser.
