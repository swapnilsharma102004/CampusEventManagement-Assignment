package com.college.eventmanagementsystem.service;

import com.college.eventmanagementsystem.entity.Registration;
import com.college.eventmanagementsystem.entity.Student;
import com.college.eventmanagementsystem.entity.Event;
import com.college.eventmanagementsystem.repository.RegistrationRepository;
import com.college.eventmanagementsystem.repository.StudentRepository;
import com.college.eventmanagementsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
    
    @Autowired
    private RegistrationRepository registrationRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }
    
    public Optional<Registration> getRegistrationById(Long id) {
        return registrationRepository.findById(id);
    }
    
    public Registration registerStudentForEvent(Long studentId, Long eventId) {
        // Check if student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        // Check if event exists
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        
        // Check if already registered
        if (registrationRepository.existsByStudentIdAndEventId(studentId, eventId)) {
            throw new RuntimeException("Student is already registered for this event");
        }
        
        Registration registration = new Registration(student, event);
        return registrationRepository.save(registration);
    }
    
    public void unregisterStudentFromEvent(Long studentId, Long eventId) {
        Registration registration = registrationRepository.findByStudentIdAndEventId(studentId, eventId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        registrationRepository.delete(registration);
    }
    
    public List<Registration> getRegistrationsByEventId(Long eventId) {
        return registrationRepository.findAll().stream()
                .filter(reg -> reg.getEvent().getId().equals(eventId))
                .toList();
    }
    
    public List<Registration> getRegistrationsByStudentId(Long studentId) {
        return registrationRepository.findAll().stream()
                .filter(reg -> reg.getStudent().getId().equals(studentId))
                .toList();
    }
}