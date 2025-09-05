package com.college.eventmanagementsystem.service;

import com.college.eventmanagementsystem.entity.Feedback;
import com.college.eventmanagementsystem.entity.Student;
import com.college.eventmanagementsystem.entity.Event;
import com.college.eventmanagementsystem.repository.FeedbackRepository;
import com.college.eventmanagementsystem.repository.StudentRepository;
import com.college.eventmanagementsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
    
    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }
    
    public Feedback submitFeedback(Long studentId, Long eventId, Integer rating, String comment) {
        // Check if student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        // Check if event exists
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        
        // Validate rating
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }
        
        Feedback feedback = new Feedback(student, event, rating, comment);
        return feedbackRepository.save(feedback);
    }
    
    public List<Feedback> getFeedbacksByEventId(Long eventId) {
        return feedbackRepository.findByEventId(eventId);
    }
    
    public List<Feedback> getFeedbacksByStudentId(Long studentId) {
        return feedbackRepository.findAll().stream()
                .filter(fb -> fb.getStudent().getId().equals(studentId))
                .toList();
    }
    
    public Double getAverageRatingByEventId(Long eventId) {
        return feedbackRepository.findAverageRatingByEventId(eventId);
    }
}