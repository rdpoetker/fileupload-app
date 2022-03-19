# fileupload-app

### 2022 Note
*This project is now outdated and shouldn't be used for reference.
I would code some parts differently now.
I'll try to modernize it soon.*

This app is sample File Upload Single Page Application (SPA) using AngularJS along with a REST service using Spring. The app is built to use Spring Boot for runtime.  

## Running the app
What you’ll need installed to run the program:

1. JDK 1.8 or later
2. Maven 3.0+

Once you have the items above installed, clone or download this project and perform the following steps:

cd to the project root directory and type:

`mvn spring-boot:run`

Open a web browser, go to [http://localhost:8080/](http://localhost:8080/)

### Notes
1. The largest file that can be uploaded is 1MB.
2. The files are simply written to a directory in the project called upload-dir.  This directory is cleaned each time the app starts.
3. File metadata is stored in an in memory h2 DB.

## Testing the app
To execute the tests:

`mvn clean test`

The test goal will execute jUnit tests and Jasmine angular tests.

## Design considerations:

This is a simple sample project for demonstrating AngularJS SPA and Spring IO/Boot frameworks.  Of course, you would not want to use this code as is in production.  For example, there is no security (authentication/authorization) and a more capable DB should be used for storing files and metadata (maybe something MongoDB).

-rdp
