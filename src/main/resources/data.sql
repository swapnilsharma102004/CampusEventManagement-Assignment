-- College Event Management System Seed Data

-- Insert sample college
INSERT INTO colleges (id, name, address) VALUES (1, 'Tech University', '123 University Ave, Tech City, TC 12345');

-- Insert sample student
INSERT INTO students (id, name, email, student_id, college_id) VALUES (1, 'John Doe', 'john.doe@techuniversity.edu', 'STU001', 1);

-- Insert sample event
INSERT INTO events (id, title, type, start_time, end_time, college_id) VALUES (1, 'Spring Boot Workshop', 'Workshop', '2024-01-15 10:00:00', '2024-01-15 16:00:00', 1);

-- Insert sample registration
INSERT INTO registrations (id, student_id, event_id, registration_time) VALUES (1, 1, 1, '2024-01-10 09:00:00');

-- Insert sample attendance
INSERT INTO attendances (id, student_id, event_id, attendance_time) VALUES (1, 1, 1, '2024-01-15 10:05:00');

-- Insert sample feedback
INSERT INTO feedbacks (id, student_id, event_id, rating, comment, feedback_time) VALUES (1, 1, 1, 5, 'Excellent workshop! Learned a lot about Spring Boot.', '2024-01-15 16:30:00');