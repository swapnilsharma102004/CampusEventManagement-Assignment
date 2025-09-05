package com.college.eventmanagementsystem.repository;

import com.college.eventmanagementsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByCollegeId(Long collegeId);
    
    @Query("SELECT s FROM Student s JOIN s.attendances a WHERE a.event.id = :eventId")
    List<Student> findStudentsWhoAttendedEvent(@Param("eventId") Long eventId);
    
    @Query("SELECT s, COUNT(a) as eventCount FROM Student s LEFT JOIN s.attendances a GROUP BY s.id ORDER BY eventCount DESC")
    List<Object[]> findStudentParticipationCounts();
}