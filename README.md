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

6. Task management

Each participant, who is part of any event, is given a specific task to do, thus we need somewhere to store this data. Here comes the Task table, which stores information for the given task, consisting of name, description, task progress: `TO_DO`, `STARTED`, `IN_PROGRESS`, `DONE`, `COMPLETED`, `ABORTED`, due date, last notified date, event id, participant id + audit fields

7. Guests

Even though we have participants in the events, we need to have guests so, we specific management for guests, in which, when you invite a guest, automatically an email with invitation is sent to the guest email. The specific data, which we store for the guest is their name, surname, email, guest type: `FAMILY`, `FRIENDS`, `COLLEAGUES`, `CO_WORKERS`, `PARTNER`, their attendance type: `GOING`, `NOT_GOING`, `CONSIDERING`, `ATTENDING` and whether or not invitation is sent and event id.

8. Vendors

We have one more different actor in our system and that is the vendor. These people are external to our system and they have specific role: `CATERER`, `PHOTOGRAPHER`, `VENUE` and more will be supported in future development. These paople are tightly connected to the event with specific contracts.

9. Contract management

These contracts stand between the vendors and the events and for each contract there is specific total price and boolean flag whether the contract has been finished or not 

10. Reviews

Last but not for each vendor, there are reviews corresponding to their performance with rating from 0 to 5, some comments and photo link if needed. 

## ER Diagram
At first you can find the ER diagram [here](https://github.com/Iliyan31/Event-Planning-and-Collaboration-Tool/blob/main/resources/Test_Event_planning_and_collaboration_tool.png). As we have already pointed out the main functionalities of our system, with ommiting the repetition we will straightly head to main idea behind it. He have used relational database because we find it really useful and secure when it comes to the personal data of the users, the correct relationship between the "tables" and last but not least we have taken this choice because of its **ACID** properties so that we will ensure the consistency of each record. Now we will talk more about the relationship types between the tables:
 
 - `Users` is *many to many relationship* to `Events`, so we have the middle table `Participans` which stands as a relation between them.
 - `Events` is the main table to which almost every table is connected to.
 - `Events` is connected with *one to many relationship* with the tables `Budgets` and `Expenses`.
 - `Events` also is connected with `Guests` with *one to many relationship*.
 - `Vendors` are connected with `Events` with *many to many relationship* with the help of the `Contracts` table and
 - Finally `Reviews` with `Vendors` with *many to one relationsip*.  

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

### Here is a list with all dependencies and their versions:  

- `Java` version 19
- `Spring Boot Starter Web`.
- `Spring boot starter data jpa`.
- `Postgresql`
- `Spring boot starter validation`
- `Lombok`
- `Spring boot starter test`
- `Mapstruct` version 1.5.3.Final
- `Spring security core` version 6.1.1
- `jjwt-api` version 0.11.5
- `jjwt-impl` version 0.11.5
- `jjwt-gson` version 0.11.5
- `Springdoc openapi starter webmvc ui` (Swagger) version 2.1.0
- `Javax mail` version 1.4.7
- `Javax activation` version 1.1.1
- `nl.martijndwars` Web push version 5.1.1
- `org.bouncycastle` bcprov-jdk15on version 1.70
- `com.vaadin` Flow server version 24.1.1
- `dev.hilla` Endpoint version 2.1.1
- `javax.annotation` Javax annotation api version 1.3.2
- `org.apache.maven.plugins.version` version 3.8.1

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
