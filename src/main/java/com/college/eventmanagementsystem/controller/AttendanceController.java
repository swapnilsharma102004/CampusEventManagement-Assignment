package com.college.eventmanagementsystem.controller;

import com.college.eventmanagementsystem.entity.Attendance;
import com.college.eventmanagementsystem.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendances")
@CrossOrigin(origins = "*")
public class AttendanceController {
    
    @Autowired
    private AttendanceService attendanceService;
    
    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        return ResponseEntity.ok(attendances);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        Optional<Attendance> attendance = attendanceService.getAttendanceById(id);
        return attendance.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Attendance> markAttendance(@RequestBody Map<String, Long> request) {
        try {
            Long studentId = request.get("studentId");
            Long eventId = request.get("eventId");
            
            if (studentId == null || eventId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            Attendance attendance = attendanceService.markAttendance(studentId, eventId);
            return ResponseEntity.status(HttpStatus.CREATED).body(attendance);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Attendance>> getAttendanceByEventId(@PathVariable Long eventId) {
        List<Attendance> attendances = attendanceService.getAttendanceByEventId(eventId);
        return ResponseEntity.ok(attendances);
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudentId(@PathVariable Long studentId) {
        List<Attendance> attendances = attendanceService.getAttendanceByStudentId(studentId);
        return ResponseEntity.ok(attendances);
    }
}