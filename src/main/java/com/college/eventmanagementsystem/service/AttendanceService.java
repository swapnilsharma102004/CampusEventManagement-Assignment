package com.college.eventmanagementsystem.service;

import com.college.eventmanagementsystem.entity.Attendance;
import com.college.eventmanagementsystem.entity.Student;
import com.college.eventmanagementsystem.entity.Event;
import com.college.eventmanagementsystem.repository.AttendanceRepository;
import com.college.eventmanagementsystem.repository.StudentRepository;
import com.college.eventmanagementsystem.repository.EventRepository;
import com.college.eventmanagementsystem.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private RegistrationRepository registrationRepository;
    
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }
    
    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }
    
    public Attendance markAttendance(Long studentId, Long eventId) {
        // Check if student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        // Check if event exists
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        
        // Check if student is registered for the event
        if (!registrationRepository.existsByStudentIdAndEventId(studentId, eventId)) {
            throw new RuntimeException("Student must be registered for the event before marking attendance");
        }
        
        // Check if already marked attendance
        if (attendanceRepository.existsByStudentIdAndEventId(studentId, eventId)) {
            throw new RuntimeException("Attendance already marked for this student and event");
        }
        
        Attendance attendance = new Attendance(student, event);
        return attendanceRepository.save(attendance);
    }
    
    public List<Attendance> getAttendanceByEventId(Long eventId) {
        return attendanceRepository.findAll().stream()
                .filter(att -> att.getEvent().getId().equals(eventId))
                .toList();
    }
    
    public List<Attendance> getAttendanceByStudentId(Long studentId) {
        return attendanceRepository.findAll().stream()
                .filter(att -> att.getStudent().getId().equals(studentId))
                .toList();
    }
}