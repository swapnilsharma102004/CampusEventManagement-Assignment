package com.college.eventmanagementsystem.controller;

import com.college.eventmanagementsystem.entity.Registration;
import com.college.eventmanagementsystem.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "*")
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationService;
    
    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        List<Registration> registrations = registrationService.getAllRegistrations();
        return ResponseEntity.ok(registrations);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable Long id) {
        Optional<Registration> registration = registrationService.getRegistrationById(id);
        return registration.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Registration> registerStudentForEvent(@RequestBody Map<String, Long> request) {
        try {
            Long studentId = request.get("studentId");
            Long eventId = request.get("eventId");
            
            if (studentId == null || eventId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            Registration registration = registrationService.registerStudentForEvent(studentId, eventId);
            return ResponseEntity.status(HttpStatus.CREATED).body(registration);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping
    public ResponseEntity<Void> unregisterStudentFromEvent(@RequestParam Long studentId, @RequestParam Long eventId) {
        try {
            registrationService.unregisterStudentFromEvent(studentId, eventId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Registration>> getRegistrationsByEventId(@PathVariable Long eventId) {
        List<Registration> registrations = registrationService.getRegistrationsByEventId(eventId);
        return ResponseEntity.ok(registrations);
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Registration>> getRegistrationsByStudentId(@PathVariable Long studentId) {
        List<Registration> registrations = registrationService.getRegistrationsByStudentId(studentId);
        return ResponseEntity.ok(registrations);
    }
}