# Task Master Pro

A comprehensive task management system was built using Spring Boot for the back end and React with Tailwind CSS and MUI for the front end. The system supports two types of users: **Admin** and **Employee/Helper**. 

## Project Overview

### Admin Features:
- **Create and Manage Tasks**: Admins can create tasks with a title, description, image URL, and deadline. 
- **Assign Tasks**: Tasks can be assigned to any registered users within the project.
- **Task Management**: Admins can view task submissions, with the ability to accept, reject, or reassign tasks.
- **Task Filtering**: Admins can filter tasks by categories such as All, Assigned, Unassigned, Pending, and Completed.

### Customer Features:
- **Task Submission**: Employees can submit their work for tasks by providing the task ID and repository URL.
- **View Assigned Tasks**: Employees can view tasks assigned to them, as well as their submissions.

## Technologies Used

### Overview:
- Used industry-leading technologies such as **Spring Boot** for backend development, **Microservices** architecture for modular service management, **Kafka** for real-time   notifications, **Eureka Server** for service discovery, and **API Gateway** as the central entry point.
- Additionally, integrated **OpenFeign** for inter-service communication, **Swagger** for API documentation, **JWT & Spring Security** for authentication and authorization, and **Redis Caching** for performance optimization.   
- Frontend technologies include **React** for UI management, styled with **Tailwind CSS** and **Material-UI** for pre-built, customizable components. The system also 
  employs **Zipkin** for distributed tracing, **Resilience4J** for fault tolerance, and **AWS S3** for file storage.

### Backend:
- **Spring Boot**: The core framework for building the backend services.
- **Microservices**:
  - **User Microservice**: Manages admin and employee users.
  - <img width="938" alt="image" src="https://github.com/user-attachments/assets/1bc2fbf2-7938-432a-b62b-c9e6f6fff25f">

  - **Task Microservice**: Handles task creation, assignment, and management.
  - <img width="948" alt="image" src="https://github.com/user-attachments/assets/367f5fe9-6982-498d-a683-16e397cbe6b5">

  - **Submission Microservice**: Manages task submissions by employees.
  - <img width="945" alt="image" src="https://github.com/user-attachments/assets/766b1742-8edd-42bc-8594-3f6366a68823">

- **Eureka Server**: Manages service discovery, ensuring all microservices are running and healthy, with load balancing support.
  - Since we have added the eureka server in api gateway, we can access it directly from the api gateway endpoint: http://localhost:5000/eureka/web
  - <img width="960" alt="image" src="<img width="937" alt="image" src="https://github.com/user-attachments/assets/ccb2b763-6746-4ab9-b785-1eb9c13e2bb4">

- **Kafka**: Kafka Service for real-time notification.
  - Implemented Kafka for real-time notifications, where the Task Service produces messages whenever a task is assigned. The Notification Service consumes these messages to send timely email notifications to users, ensuring immediate and effective communication of task assignments

- **API Gateway**: Serves as the entry point for all microservices.
  - All the API endpoints are accessible with port 5000.
- **OpenFeign**: Facilitates communication between microservices.
- **Swagger**: Provides an interactive interface to access and test API endpoints.
- **JWT & Spring Security**: Implements authentication and role-based authorization.
- **Redis Caching**: API-level caching to optimize performance by reducing repeated calls to controllers.
  - <img width="817" alt="image" src="https://github.com/user-attachments/assets/15ddc9ee-bd6b-4340-b19d-7e52493c1ff5">
    - Custom annotation "HttpCacheable" was created which annotates the controllers which should cache its response.
- **Filters**:
  - **CorrelationId Filter**: Adds a mandatory header for tracking requests in logs.
    - Correlation header should be added with each request. This id can be used to check the logs and easily detect the error for a particular request.
  - **Auth Filter**: Manages user authentication.
    - Except Login and register endpoint, each request should have Auth header included or else a 401 error will be returned.
  - **Redis Cache Filter**: Handles API-level caching.
    - If data is present in the cache then it should be returned directly from the middleware, or else it should continue the flow and the returned response should be added to the cache for the next request to this endpoint.
- **AOP (Aspect-Oriented Programming)**: Logs method calls before and after execution of every method in Controller, Service and Repo layer for ease of logging and tracking the flow of control. 
- **AWS S3**: Stores user profile images.
- **Email Verification**: Validates user email addresses upon registration.

- **Resilience4J implementation:**
  - Implemented Resilience4j to enhance fault tolerance in the application by incorporating `@CircuitBreakers` mechanisms for external service calls and additionally `@Retry` and `@TimeLimiter` can further be added for fine-grained control over fault tolerance.
  
- **Zipkin**:
  - Integrated Zipkin for distributed tracing, enabling real-time tracking of service requests and performance analysis across microservices, allowing for better monitoring, troubleshooting, and optimization of service interactions within the application
  - <img width="960" src="./Images/zipkin.png" alt="Zipkin"/>

### Frontend:
- **React**: Manages the state and UI of the application.
- **Tailwind CSS**: Provides utility-first CSS styling.
- **MUI (Material-UI)**: Offers pre-built, customizable components for the user interface.

## UI Components
- **NavBar**: Navigation bar for easy access to different sections.
- **Task**: Displays task details.
- **TaskList**: Shows a list of tasks based on filters.
- **Submissions**: Allows employees to submit their work.
- **UserList**: Displays a list of registered users.
- Many other components were created to support the above primary components.
- **Admin View:**
  <div style="display: flex; flex-wrap: wrap;">
    <img src="./Images/All Tasks visible to admin.png" alt="All Tasks" style="width: 40%; margin: 1%;" />
    <img src="./Images/Login.png" alt="Login" style="width: 40%; margin: 1%;" />
    <img src="./Images/Pending tasks filter.png" alt="Pending Tasks" style="width: 40%; margin: 1%;" />
    
    <img src="./Images/Approval of task submissions.png" alt="Approve or Reject the tasks" style="width: 40%; margin: 1%;" />
  </div>
- **Customer View:**
  <div style="display: flex; flex-wrap: wrap;">
    <img src="./Images/All assigned task for customer role user.png" alt="Assigned Tasks in customer view" style="width: 40%; margin: 1%;" />
    <img src="./Images/Submit the task with repo link.png" alt="Submit the task" style="width: 40%; margin: 1%;" />
  </div>


## Getting Started

### Prerequisites
- Java 11 or higher
- Node.js
- npm or yarn
- Redis
- AWS account for S3
- Kafka
- Zipkins (docker)

### Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/task-management-system.git
   cd task-management-system
   ```
   
2. **Environment Variables:**
   
   Refer to the attached env.example file to check the environment variables used in the microservices.
   
4. **Backend Setup:**
   - Navigate to each microservice directory and run:
     ```bash
     ./mvnw clean install
      ```
   - Start the Eureka Server and API Gateway.
   - Start each microservice.
     
5. **Frontend Setup:**
   - Navigate to the frontend directory and install dependencies:
     ```bash
     npm install
     ```
   - Start the React application:
     ```bash
     npm start
     ```
### Usage
  - **Admin:**: Log in as an admin to manage tasks and view submissions
  - **Customer**: Log in as an employee to view assigned tasks and submit work



