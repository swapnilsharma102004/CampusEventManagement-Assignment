package com.college.eventmanagementsystem.service;

import com.college.eventmanagementsystem.entity.Student;
import com.college.eventmanagementsystem.repository.StudentRepository;
import com.college.eventmanagementsystem.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CollegeRepository collegeRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public List<Student> getStudentsByCollegeId(Long collegeId) {
        return studentRepository.findByCollegeId(collegeId);
    }
    
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Student createStudent(Student student) {
        // Verify college exists
        collegeRepository.findById(student.getCollege().getId())
                .orElseThrow(() -> new RuntimeException("College not found with id: " + student.getCollege().getId()));
        
        return studentRepository.save(student);
    }
    
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setStudentId(studentDetails.getStudentId());
        
        if (studentDetails.getCollege() != null) {
            collegeRepository.findById(studentDetails.getCollege().getId())
                    .orElseThrow(() -> new RuntimeException("College not found with id: " + studentDetails.getCollege().getId()));
            student.setCollege(studentDetails.getCollege());
        }
        
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        studentRepository.delete(student);
    }
}