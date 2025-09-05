package com.college.eventmanagementsystem.repository;

import com.college.eventmanagementsystem.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCollegeId(Long collegeId);
    
    @Query("SELECT e, COUNT(r) as registrationCount FROM Event e LEFT JOIN e.registrations r GROUP BY e.id ORDER BY registrationCount DESC")
    List<Object[]> findEventsOrderedByRegistrationCount();
    
    @Query("SELECT e, COUNT(a) as attendanceCount, COUNT(r) as registrationCount FROM Event e LEFT JOIN e.attendances a LEFT JOIN e.registrations r GROUP BY e.id")
    List<Object[]> findEventAttendanceStats();
    
    @Query("SELECT e, AVG(f.rating) as avgRating FROM Event e LEFT JOIN e.feedbacks f GROUP BY e.id ORDER BY avgRating DESC")
    List<Object[]> findEventAverageRatings();
}