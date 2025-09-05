package com.college.eventmanagementsystem.controller;

import com.college.eventmanagementsystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    @GetMapping("/event-popularity")
    public ResponseEntity<List<Map<String, Object>>> getEventPopularityReport() {
        List<Map<String, Object>> report = reportService.getEventPopularityReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/attendance")
    public ResponseEntity<List<Map<String, Object>>> getAttendanceReport() {
        List<Map<String, Object>> report = reportService.getAttendanceReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/feedback")
    public ResponseEntity<List<Map<String, Object>>> getFeedbackReport() {
        List<Map<String, Object>> report = reportService.getFeedbackReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/student-participation")
    public ResponseEntity<List<Map<String, Object>>> getStudentParticipationReport() {
        List<Map<String, Object>> report = reportService.getStudentParticipationReport();
        return ResponseEntity.ok(report);
    }
}