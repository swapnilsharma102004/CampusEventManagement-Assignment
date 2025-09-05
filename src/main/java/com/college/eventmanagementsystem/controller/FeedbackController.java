package com.college.eventmanagementsystem.controller;

import com.college.eventmanagementsystem.entity.Feedback;
import com.college.eventmanagementsystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin(origins = "*")
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;
    
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        return feedback.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Feedback> submitFeedback(@RequestBody Map<String, Object> request) {
        try {
            Long studentId = Long.valueOf(request.get("studentId").toString());
            Long eventId = Long.valueOf(request.get("eventId").toString());
            Integer rating = Integer.valueOf(request.get("rating").toString());
            String comment = (String) request.get("comment");
            
            if (studentId == null || eventId == null || rating == null) {
                return ResponseEntity.badRequest().build();
            }
            
            Feedback feedback = feedbackService.submitFeedback(studentId, eventId, rating, comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByEventId(@PathVariable Long eventId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByEventId(eventId);
        return ResponseEntity.ok(feedbacks);
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByStudentId(@PathVariable Long studentId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByStudentId(studentId);
        return ResponseEntity.ok(feedbacks);
    }
    
    @GetMapping("/event/{eventId}/average-rating")
    public ResponseEntity<Map<String, Object>> getAverageRatingByEventId(@PathVariable Long eventId) {
        Double averageRating = feedbackService.getAverageRatingByEventId(eventId);
        Map<String, Object> response = Map.of(
            "eventId", eventId,
            "averageRating", averageRating != null ? averageRating : 0.0
        );
        return ResponseEntity.ok(response);
    }
}