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
  - **Task Microservice**: Handles task creation, assignment, and management.
  - **Submission Microservice**: Manages task submissions by employees.
- **Eureka Server**: Manages service discovery, ensuring all microservices are running and healthy, with load balancing support.
- **API Gateway**: Serves as the entry point for all microservices.
- **OpenFeign**: Facilitates communication between microservices.
- **Swagger**: Provides an interactive interface to access and test API endpoints.
- **JWT & Spring Security**: Implements authentication and role-based authorization.
- **Redis Caching**: API-level caching to optimize performance by reducing repeated calls to controllers.
- **Filters**:
  - **CorrelationId Filter**: Adds a mandatory header for tracking requests in logs.
  - **Auth Filter**: Manages user authentication.
  - **Redis Cache Filter**: Handles API-level caching.
- **AOP (Aspect-Oriented Programming)**: Logs method calls before and after execution.
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

## Getting Started

### Prerequisites
- Java 11 or higher
- Node.js
- npm or yarn
- Redis
- AWS account for S3
- Eureka Server

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



