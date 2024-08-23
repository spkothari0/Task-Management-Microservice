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
  - <img width="960" alt="image" src="https://github.com/user-attachments/assets/ed9c5973-68b9-4307-a2b3-f3f51332c4ff">

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

## Getting Started

### Prerequisites
- Java 11 or higher
- Node.js
- npm or yarn
- Redis
- AWS account for S3

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



