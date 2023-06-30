# EventCrafter 🕐✈️🌴
## Summary

**EventCrafter** is our project developed for the **Web Development with Java** course at FMI. It is a web-based platform that simplifies the event planning process by allowing users to collaborate with others, manage tasks, and track progress. It is developed with **Spring Boot** v3.1.0 for the backend, and **Angular** for the frontend. The software allows event creation and managment by allocating tasks, planning budgets, etc. 

## Functionalities

The following functionlaities are supported by the platform:
1. User Managment
   
In **EventCrafter** user profiles can be created. Each user is associated with fields as `id`, `username`, `password`, `name`, `surname`, `email`, `address`. Audit fields as `createdBy`, `creationTime`, `updatedBy` and `lastUpdatedTime` are used for tracking changes. Soft deletion is achieved by `deleted` boolean variable which gets true whenever user is tried to be deleted.

2. Event Managment

Event creation is supported by the software. CRUD operations are implemented for each model. Events can be created, edited and deleted. Each event is associated characterised by `id`, `name`, `date`, `location`, `description`, as well as audit and soft deletion fields.

3. Participant managment

In order to invite collaborators and manage user roles in **EventCrafter** you participants to events can be added. With the help of a form all users can be visualized and choosen from. There are three user roles: **Creator**, **Planner** and **Guest**. The one who creates an event is automatically assigned with label `CREATOR`. Planners and guests can be invited. The activities that can be done from the `PLANNER` coincide with these of the `CREATOR` except for the opportunity to delete event. The `GUEST` can only see the event details and leave the event if they'd like to.    

4. Expense and Budget Managment

For the purposes of budget tracking models for representing expenses and budgets are used. Each budget can have one of the following categories: `ALL`, `DECORATION`, `CATERER`, `PHOTOGRAPHER`, `VENUE`, `OTHER`. There can be only one budget of each catgegory. Many expenses can be associated to each budget. The total amount of budget cannot exceed the overall budget. The total amount of expenses cannot exceed the budget for the relevant category. CRUD operations are supported as well.

5. Authentication and Authorization

Authentication is managed by the interchange of JWT tokens. In the payload of that token, data for the currently loged user is preserved. That is used when it comes to authorization. The authorization policy is done by internal filtering and is the following: all `GET` requests are allowed, as well as all `POST` requests that are intended for the user controller (as a mean for registration). All other requests require **Authorization** header with value in the form of **Bearer <token>** where <token> is the token associated to the current session returned by the result of the login method. When it comes to UI in **Angular**, the browser's local storage is used for storing token information.  

## ER Diagram
## Architecture & Rest API

The preferred and used architecture for the Spring Boot application is the layered architecture. For direct communication with the database JPA repository interfaces are used. There are service classes which communicate on top of them. Facade services are used when it comes to operations which require collaboration with more than one relation of the database schema. The implemented REST controllers are the access for the client to use the API.

For all of the tables in the database there are related models, dto-s, mappers, repositories, services and controllers. **Swagger** documentation of the API can be seen and tested from [localhost:8080/swagger.html](http://localhost:8080/swagger.html) when the Spring Boot Application is started.  

## Backend
## Key Features
## Startup Steps
## Libraries
## Way of Working

|||
|:------------------:|:-------------------:|
|||
|||
|||
|||
|||
|||
|||
