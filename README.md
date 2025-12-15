# Wellness Booking System
This is a simple wellness booking system that allows users to book appointments for various wellness services.
The system is built using Java SpringBoot and MongoDB
## Features
- User registration and authentication
- Browse events (by ID and view all) *view all only available to HR roles
- Propose dates for events
- Accept or reject proposed dates (VENDOR and HR role)
## Technologies Used
- Java SpringBoot
- MongoDB
- Maven
- Spring Security
- Swagger (for API documentation)
## Prerequisites
- Java 21 
- Maven
- MongoDB (YAML file for local setup can be found in the project root)
- IDE: IntelliJ IDEA
- Postman (for testing APIs)
- ------
## Getting Started
1. Clone the repository
2. Update properties file with your MongoDB connection details (src/main/resources/application.properties)
3. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```
5. The application will be accessible at `http://localhost:8080`
## API Endpoints
1. Authentication:
- `POST /api/auth/register` - Register a new user
1. Events:
- `POST /api/events` - Create events (HR role only)
- `POST /api/events/{id}/reject` - Reject a proposed date (VENDOR and HR role)
- `POST /api/events/{id}/accept` - Approve a proposed date (VENDOR and HR role)
- `GET /api/events/{id}` - View event by ID (Vendor able to view only their events)
- `GET /api/events/all` - View all events (HR role only)
- 1. User:
- `GET user/users` - View all registered users (HR role only)
- ------
## Testing
- Swagger UI is available at `http://localhost:8080/swagger-ui.html` for viewing/testing the API endpoints.
- Unit and Integration Tests:
  - Test classes are located in `src/test/java/com/example/wellnessbooking`
  - Execute tests using Maven:
     ```
     mvn test
     ```
- Manual Testing:
  - You can use Postman or any other API testing tool to test the endpoints.
## ER Diagram
An ER diagram representing the database schema can be found in the `docs` folder.
![56f3da1b-1525-4541-b8d4-72e62f0557d6.png](/docs/56f3da1b-1525-4541-b8d4-72e62f0557d6.png)
## Future Improvements
- The Authentication and Event has been made to an interface to allow for SOLID principles and future extensibility
- Implement JWT for stateless authentication.
- Implement email notifications for booking confirmations and reminders.
- Add more detailed logging and monitoring.
- Enhance security features (e.g., password reset, account lockout).
- Implement a frontend interface using React or Angular.
