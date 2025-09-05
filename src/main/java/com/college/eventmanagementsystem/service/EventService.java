package com.college.eventmanagementsystem.service;

import com.college.eventmanagementsystem.entity.Event;
import com.college.eventmanagementsystem.repository.EventRepository;
import com.college.eventmanagementsystem.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private CollegeRepository collegeRepository;
    
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
    public List<Event> getEventsByCollegeId(Long collegeId) {
        return eventRepository.findByCollegeId(collegeId);
    }
    
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
    
    public Event createEvent(Event event) {
        // Verify college exists
        collegeRepository.findById(event.getCollege().getId())
                .orElseThrow(() -> new RuntimeException("College not found with id: " + event.getCollege().getId()));
        
        return eventRepository.save(event);
    }
    
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        
        event.setTitle(eventDetails.getTitle());
        event.setType(eventDetails.getType());
        event.setStartTime(eventDetails.getStartTime());
        event.setEndTime(eventDetails.getEndTime());
        
        if (eventDetails.getCollege() != null) {
            collegeRepository.findById(eventDetails.getCollege().getId())
                    .orElseThrow(() -> new RuntimeException("College not found with id: " + eventDetails.getCollege().getId()));
            event.setCollege(eventDetails.getCollege());
        }
        
        return eventRepository.save(event);
    }
    
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        
        eventRepository.delete(event);
    }
}