# EventCrafter 🕐✈️
## Summary

**EventCrafter** is project developed for the **Web Development with Java** course at FMI. It is a web-based platform that simplifies the event planning process by allowing users to collaborate with others, manage tasks, and track progress. It is developed with **Spring Boot** v3.1.0 for the backend, and **Angular** for the frontend. Insummary, the software allows event creation and managment by allocating tasks, planning budgets, etc. 

## Functionalities

The following functionlaities are supported by the platform:
1. User Managment
   
In **EventCrafter** user profiles can be created. Each user is associated with fields such as `id`, `username`, `password`, `name`, `surname`, `email`, `address`. Audit fields as `createdBy`, `creationTime`, `updatedBy` and `lastUpdatedTime` are used for tracking changes. Soft deletion is achieved by `deleted` boolean variable which gets true whenever user is tried to be deleted.

2. Event Management

Event creation is supported by the app. CRUD operations are implemented for each of the models. Events can be created, edited and deleted. Each event is characterised by `id`, `name`, `date`, `location`, `description`, as well as audit and soft deletion fields.

3. Participant Management

In order to invite collaborators and manage user roles in **EventCrafter** participants to events can be added. With the help of a form all users can be visualized and choosen from. There are three user roles: **Creator**, **Planner** and **Guest**. The one who creates an event is automatically assigned with label `CREATOR`. Planners and guests can be invited to. The activities that can be done from the `PLANNER` coincide with these of the `CREATOR`, except for the opportunity to delete event. The `GUEST` can only see the event details and leave the event if they'd like to.    

4. Expense and Budget Management

Models for representation of expenses and budgets are used for the purposes of budget tracking. Each budget can have one of the following categories: `ALL`, `DECORATION`, `CATERER`, `PHOTOGRAPHER`, `VENUE`, `OTHER`. There can be only one budget of each catgegory. Many expenses can be associated to each budget. The total amount of budget cannot exceed the overall budget. The total amount of expenses cannot exceed the budget for the relevant category. CRUD operations are supported as well.

5. Authentication and Authorization

Authentication is managed by the interchange of JWT tokens. In the payload of that token, data for the currently loged user is preserved. That is used when it comes to authorization. The authorization policy is done by internal filtering and is as follows: all `GET` requests are allowed, as well as all `POST` requests that are intended for the user controller (as a mean for registration). All other requests require **Authorization** header with value in the form of **Bearer *<token>*** where *<token>* is the token associated to the current session returned by the result of the login method. When it comes to the UI in **Angular**, the browser's local storage is used for storing token information.  

## ER Diagram
## Architecture & Rest API

The preferred architecture for the **Spring Boot** application is the layered architecture. The idea behind it is that modules or components with similar functionalities are organized into horizontal layers. As a result, each layer performs a specific role within the application. For direct communication with the database JPA Repository Interfaces are used. There are service classes which communicate on top of them. Facade services are used when it comes to operations which require collaboration with more than one relation of the database schema. The implemented REST Controllers are the access and the key for the client to use the API.

For all of the tables in the database there are related models, dto-s, mappers, repositories, services and controllers. **Swagger** documentation of the API can be seen and tested from [localhost:8080/swagger.html](http://localhost:8080/swagger.html) when the **Spring Boot** application is started. Another available documentation can be found [here]().  

## Backend
## Key Features

The fundamental features of the project are the **Spring Boot** beans and other classes which lay the foundations of the app. Our key features are all the **business logic** which can be found in each of the services, especially the facade services. Moreover, the **frontend** developed with **HTML**, **CSS** and **TypeScript** on **Angular** was definitely a challange which really worth it. Most of the endpoints can be seen in the UI wrapped inside graphical elements. Another key feature is the **authentication** and **authorization** procedure. Working with tokens and passing them with every request demanded paying attention to small details. Problems with CORS policy were among the most frequently faced issues when it came to communication with applications on different ports. The opportunity of **sending invitations as emails** to each guest is another distinct feature of our platform which improves additionally the functionality.  

## Startup Steps

Each feature of the app was tested on two different databases - **PostgreSQL** and **MySQL**. In the `pom.xml` and `application.properties` files the configurations for the variant with the **PostgreSQL** can be found. All of the dependencies are required in order to start the application. Local database needs to be available and set with preferred names and passwords which to be replaced afterwards in the `application.properties` file. For sending emails valid email and password of the sender have to be provided in the `application.properties`. For working with notifications valid public and private keys need to be placed there as well. They can be easily generated with the help of terminal by running `npx web-push generate-vapid-keys [--json]`. 

The **Angular** project needs to be run seperately as well. For visualization the **Angular Live Development Server** is usually listening on [localhost:4200](http://localhost:4200/). For proper communication with the backend, it is highly recommended for the backend to be started first so that the requests sent from the browser in the frontend can be delivered and answered in correct way.

## Libraries
## Way of Working

The project was realized in working as a team. There were regular meetings held mainly for syncing, discussing changes, different visions and improvements. The main division of work was purely based on the domain (the same division is valid for the **Spring Boot** application and **Angular** project) and is given in the following table:

|**Stefan Velev**||
|:------------------:|:-------------------:|
|User Management||
|Participant Management||
|Event Management||
|Budget Management||
|Expense Management||
|Authentication & Authorization||

For more details, see our **ClickUp** WorkSpace: ...
