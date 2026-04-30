package com.school.management.api.service;

import com.school.management.api.entity.Student;
import com.school.management.api.model.requstModel.StudentRequestDto;
import com.school.management.api.model.responseModel.StudentResponseDto;
import com.school.management.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public StudentResponseDto createStudent(StudentRequestDto requestDto){
        Student student = new Student();
        student.setName(requestDto.getName());
        student.setAdmissionNumber(requestDto.getAdmissionNumber());
        student.setRollNumber(requestDto.getRollNumber());
        student.setDateOfBirth(requestDto.getDateOfBirth());
        student.setGender(requestDto.getGender());
        student.setBloodGroup(requestDto.getBloodGroup());

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        Student savedStudent =studentRepository.save(student);

        return StudentResponseDto.builder()
                .id(savedStudent.getId())
                        .name(savedStudent.getName())
                        .admissionNumber(savedStudent.getAdmissionNumber())
                        .rollNumber(savedStudent.getRollNumber())
                        .dateOfBirth(savedStudent.getDateOfBirth())
                        .gender(savedStudent.getGender())
                        .bloodGroup(savedStudent.getBloodGroup())
                        .createdAt(savedStudent.getCreatedAt())
                        .updatedAt(savedStudent.getUpdatedAt())
                        .build();

    }

    public StudentResponseDto updateStudent(Long id , StudentRequestDto requestDto){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        student.setName(requestDto.getName());
        student.setAdmissionNumber(requestDto.getAdmissionNumber());
        student.setRollNumber(requestDto.getRollNumber());
        student.setDateOfBirth(requestDto.getDateOfBirth());
        student.setGender(requestDto.getGender());
        student.setBloodGroup(requestDto.getBloodGroup());

        student.setUpdatedAt(LocalDateTime.now());
        Student updatedStudent = studentRepository.save(student);
        return StudentResponseDto.builder()
                .id(updatedStudent.getId())
                .name(updatedStudent.getName())
                .admissionNumber(updatedStudent.getAdmissionNumber())
                .rollNumber(updatedStudent.getRollNumber())
                .dateOfBirth(updatedStudent.getDateOfBirth())
                .gender(updatedStudent.getGender())
                .bloodGroup(updatedStudent.getBloodGroup())
                .createdAt(updatedStudent.getCreatedAt())
                .updatedAt(updatedStudent.getUpdatedAt())
                .build();
    }

    public String deleteStudent(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        studentRepository.delete(student);
        return "Student deleted successfully with id: " + id;
    }

    public List<StudentResponseDto> getAllStudents() {

        List<Student> students = studentRepository.findAll();

        return students.stream().map(student -> StudentResponseDto.builder()
                .id(student.getId())
                .name(student.getName())
                .admissionNumber(student.getAdmissionNumber())
                .rollNumber(student.getRollNumber())
                .dateOfBirth(student.getDateOfBirth())
                .gender(student.getGender())
                .bloodGroup(student.getBloodGroup())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build()
        ).collect(Collectors.toList());
    }


    public StudentResponseDto getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        return StudentResponseDto.builder()
                .id(student.getId())
                .name(student.getName())
                .admissionNumber(student.getAdmissionNumber())
                .rollNumber(student.getRollNumber())
                .dateOfBirth(student.getDateOfBirth())
                .gender(student.getGender())
                .bloodGroup(student.getBloodGroup())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
