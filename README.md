# College Event Management System

A comprehensive Spring Boot application for managing college events, student registrations, attendance tracking, and feedback collection.

## Features

- **College Management** - Add and manage colleges
- **Student Management** - Add students linked to colleges
- **Event Management** - Create events with title, type, start/end times
- **Student Registration** - Register students for events (unique per student per event)
- **Attendance Tracking** - Mark attendance for registered students
- **Feedback Collection** - Students can submit ratings (1-5) and comments
- **Reports** - Various reports for event popularity, attendance, feedback, and student participation

## Technology Stack

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- SQLite Database
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### College Management

#### Get All Colleges
```bash
curl -X GET http://localhost:8080/api/colleges
```

#### Get College by ID
```bash
curl -X GET http://localhost:8080/api/colleges/1
```

#### Create College
```bash
curl -X POST http://localhost:8080/api/colleges \
  -H "Content-Type: application/json" \
  -d '{
    "name": "State University",
    "address": "456 College St, State City, SC 54321"
  }'
```

#### Update College
```bash
curl -X PUT http://localhost:8080/api/colleges/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated University",
    "address": "789 New Address, City, ST 12345"
  }'
```

#### Delete College
```bash
curl -X DELETE http://localhost:8080/api/colleges/1
```

### Student Management

#### Get All Students
```bash
curl -X GET http://localhost:8080/api/students
```

#### Get Students by College
```bash
curl -X GET http://localhost:8080/api/students/college/1
```

#### Create Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane.smith@techuniversity.edu",
    "studentId": "STU002",
    "college": {
      "id": 1
    }
  }'
```

#### Update Student
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "email": "john.updated@techuniversity.edu",
    "studentId": "STU001",
    "college": {
      "id": 1
    }
  }'
```

#### Delete Student
```bash
curl -X DELETE http://localhost:8080/api/students/1
```

### Event Management

#### Get All Events
```bash
curl -X GET http://localhost:8080/api/events
```

#### Get Events by College
```bash
curl -X GET http://localhost:8080/api/events/college/1
```

#### Create Event
```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Java Programming Workshop",
    "type": "Workshop",
    "startTime": "2024-02-01T09:00:00",
    "endTime": "2024-02-01T17:00:00",
    "college": {
      "id": 1
    }
  }'
```

#### Update Event
```bash
curl -X PUT http://localhost:8080/api/events/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Advanced Spring Boot Workshop",
    "type": "Workshop",
    "startTime": "2024-01-15T10:00:00",
    "endTime": "2024-01-15T16:00:00",
    "college": {
      "id": 1
    }
  }'
```

#### Delete Event
```bash
curl -X DELETE http://localhost:8080/api/events/1
```

### Registration Management

#### Get All Registrations
```bash
curl -X GET http://localhost:8080/api/registrations
```

#### Register Student for Event
```bash
curl -X POST http://localhost:8080/api/registrations \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "eventId": 1
  }'
```

#### Unregister Student from Event
```bash
curl -X DELETE "http://localhost:8080/api/registrations?studentId=1&eventId=1"
```

#### Get Registrations by Event
```bash
curl -X GET http://localhost:8080/api/registrations/event/1
```

#### Get Registrations by Student
```bash
curl -X GET http://localhost:8080/api/registrations/student/1
```

### Attendance Management

#### Get All Attendances
```bash
curl -X GET http://localhost:8080/api/attendances
```

#### Mark Attendance
```bash
curl -X POST http://localhost:8080/api/attendances \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "eventId": 1
  }'
```

#### Get Attendance by Event
```bash
curl -X GET http://localhost:8080/api/attendances/event/1
```

#### Get Attendance by Student
```bash
curl -X GET http://localhost:8080/api/attendances/student/1
```

### Feedback Management

#### Get All Feedbacks
```bash
curl -X GET http://localhost:8080/api/feedbacks
```

#### Submit Feedback
```bash
curl -X POST http://localhost:8080/api/feedbacks \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "eventId": 1,
    "rating": 4,
    "comment": "Great event, very informative!"
  }'
```

#### Get Feedbacks by Event
```bash
curl -X GET http://localhost:8080/api/feedbacks/event/1
```

#### Get Feedbacks by Student
```bash
curl -X GET http://localhost:8080/api/feedbacks/student/1
```

#### Get Average Rating for Event
```bash
curl -X GET http://localhost:8080/api/feedbacks/event/1/average-rating
```

### Reports

#### Event Popularity Report
```bash
curl -X GET http://localhost:8080/reports/event-popularity
```

#### Attendance Report
```bash
curl -X GET http://localhost:8080/reports/attendance
```

#### Feedback Report
```bash
curl -X GET http://localhost:8080/reports/feedback
```

#### Student Participation Report
```bash
curl -X GET http://localhost:8080/reports/student-participation
```

## Database

The application uses SQLite database stored in `event_system.db` file. The database schema is automatically created on startup with sample data.

## Constraints

- A student can register only once per event
- A student can attend only once per event
- Feedback rating must be between 1 and 5
- Student email and student ID must be unique
- College name is required

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/college/eventmanagementsystem/
│   │       ├── controller/     # REST controllers
│   │       ├── entity/         # JPA entities
│   │       ├── repository/     # Data repositories
│   │       ├── service/        # Business logic services
│   │       └── EventManagementSystemApplication.java
│   └── resources/
│       ├── application.properties
│       ├── schema.sql          # Database schema
│       └── data.sql            # Seed data
├── pom.xml                     # Maven configuration
└── README.md                   # This file
```

## Testing the Application

1. Start the application using `./mvnw spring-boot:run`
2. Use the provided curl commands to test the API endpoints
3. Check the database file `event_system.db` to verify data persistence
4. Test the reports endpoints to see aggregated data

## Sample Workflow

1. Create a college
2. Add students to the college
3. Create events for the college
4. Register students for events
5. Mark attendance for registered students
6. Collect feedback from students
7. Generate reports to analyze data