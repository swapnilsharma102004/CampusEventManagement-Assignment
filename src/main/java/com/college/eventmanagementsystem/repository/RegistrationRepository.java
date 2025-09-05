package com.college.eventmanagementsystem.repository;

import com.college.eventmanagementsystem.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByStudentIdAndEventId(Long studentId, Long eventId);
    
    Optional<Registration> findByStudentIdAndEventId(Long studentId, Long eventId);
    
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.event.id = :eventId")
    Long countByEventId(@Param("eventId") Long eventId);
}