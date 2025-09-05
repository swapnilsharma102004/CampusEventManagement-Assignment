package com.college.eventmanagementsystem.repository;

import com.college.eventmanagementsystem.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsByStudentIdAndEventId(Long studentId, Long eventId);
    
    Optional<Attendance> findByStudentIdAndEventId(Long studentId, Long eventId);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.event.id = :eventId")
    Long countByEventId(@Param("eventId") Long eventId);
}