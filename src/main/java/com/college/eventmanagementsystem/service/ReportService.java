package com.college.eventmanagementsystem.service;

import com.college.eventmanagementsystem.repository.EventRepository;
import com.college.eventmanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Map<String, Object>> getEventPopularityReport() {
        List<Object[]> results = eventRepository.findEventsOrderedByRegistrationCount();
        
        return results.stream()
                .map(result -> {
                    Map<String, Object> eventData = Map.of(
                        "eventId", ((com.college.eventmanagementsystem.entity.Event) result[0]).getId(),
                        "eventTitle", ((com.college.eventmanagementsystem.entity.Event) result[0]).getTitle(),
                        "eventType", ((com.college.eventmanagementsystem.entity.Event) result[0]).getType(),
                        "collegeName", ((com.college.eventmanagementsystem.entity.Event) result[0]).getCollege().getName(),
                        "registrationCount", result[1]
                    );
                    return eventData;
                })
                .collect(Collectors.toList());
    }
    
    public List<Map<String, Object>> getAttendanceReport() {
        List<Object[]> results = eventRepository.findEventAttendanceStats();
        
        return results.stream()
                .map(result -> {
                    Long registrationCount = (Long) result[2];
                    Long attendanceCount = (Long) result[1];
                    Double attendancePercentage = registrationCount > 0 ? 
                        (attendanceCount.doubleValue() / registrationCount.doubleValue()) * 100 : 0.0;
                    
                    Map<String, Object> eventData = Map.of(
                        "eventId", ((com.college.eventmanagementsystem.entity.Event) result[0]).getId(),
                        "eventTitle", ((com.college.eventmanagementsystem.entity.Event) result[0]).getTitle(),
                        "collegeName", ((com.college.eventmanagementsystem.entity.Event) result[0]).getCollege().getName(),
                        "registrationCount", registrationCount,
                        "attendanceCount", attendanceCount,
                        "attendancePercentage", Math.round(attendancePercentage * 100.0) / 100.0
                    );
                    return eventData;
                })
                .collect(Collectors.toList());
    }
    
    public List<Map<String, Object>> getFeedbackReport() {
        List<Object[]> results = eventRepository.findEventAverageRatings();
        
        return results.stream()
                .map(result -> {
                    Double avgRating = (Double) result[1];
                    Map<String, Object> eventData = Map.of(
                        "eventId", ((com.college.eventmanagementsystem.entity.Event) result[0]).getId(),
                        "eventTitle", ((com.college.eventmanagementsystem.entity.Event) result[0]).getTitle(),
                        "collegeName", ((com.college.eventmanagementsystem.entity.Event) result[0]).getCollege().getName(),
                        "averageRating", avgRating != null ? Math.round(avgRating * 100.0) / 100.0 : 0.0
                    );
                    return eventData;
                })
                .collect(Collectors.toList());
    }
    
    public List<Map<String, Object>> getStudentParticipationReport() {
        List<Object[]> results = studentRepository.findStudentParticipationCounts();
        
        return results.stream()
                .map(result -> {
                    Map<String, Object> studentData = Map.of(
                        "studentId", ((com.college.eventmanagementsystem.entity.Student) result[0]).getId(),
                        "studentName", ((com.college.eventmanagementsystem.entity.Student) result[0]).getName(),
                        "studentEmail", ((com.college.eventmanagementsystem.entity.Student) result[0]).getEmail(),
                        "collegeName", ((com.college.eventmanagementsystem.entity.Student) result[0]).getCollege().getName(),
                        "eventsAttended", result[1]
                    );
                    return studentData;
                })
                .collect(Collectors.toList());
    }
}