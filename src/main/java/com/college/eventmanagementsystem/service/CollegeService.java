package com.college.eventmanagementsystem.service;

import com.college.eventmanagementsystem.entity.College;
import com.college.eventmanagementsystem.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollegeService {
    
    @Autowired
    private CollegeRepository collegeRepository;
    
    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }
    
    public Optional<College> getCollegeById(Long id) {
        return collegeRepository.findById(id);
    }
    
    public College createCollege(College college) {
        return collegeRepository.save(college);
    }
    
    public College updateCollege(Long id, College collegeDetails) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found with id: " + id));
        
        college.setName(collegeDetails.getName());
        college.setAddress(collegeDetails.getAddress());
        
        return collegeRepository.save(college);
    }
    
    public void deleteCollege(Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found with id: " + id));
        
        collegeRepository.delete(college);
    }
}