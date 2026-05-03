package com.school.management.api.service;

import com.school.management.api.entity.Student;
import com.school.management.api.model.requstModel.StudentRequestDto;
import com.school.management.api.model.responseModel.StudentResponseDto;
import com.school.management.api.repository.StudentRepository;
import com.school.management.api.repository.UserRepository;
import com.school.management.api.service.mapper.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    MapperService mapperService;

    @Autowired
    UserRepository userRepository;

    public StudentResponseDto createStudent(StudentRequestDto requestDto){

        Student student = new Student();
        student.setName(requestDto.getName());
        student.setStudentId(requestDto.getSchoolId() + "_" + requestDto.getAdmissionNumber());
        student.setSchoolID(requestDto.getSchoolId());
        student.setAdmissionNumber(requestDto.getAdmissionNumber());
        student.setRollNumber(requestDto.getRollNumber());
        student.setDateOfBirth(requestDto.getDateOfBirth());
        student.setGender(requestDto.getGender());
        student.setClassId(requestDto.getClassId());
        student.setBloodGroup(requestDto.getBloodGroup());
        if(requestDto.getUserId() != null){
            student.setParentId(requestDto.getUserId());
        }
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        student.setAcademicYear(MapperService.getAcademicYear());

        Student savedStudent =studentRepository.save(student);

        return mapperService.toCreateStudent(savedStudent);

    }

//    public StudentResponseDto updateStudent(Long id , StudentRequestDto requestDto){
//        Student student = studentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
//
//        student.setName(requestDto.getName());
//        student.setAdmissionNumber(requestDto.getAdmissionNumber());
//        student.setRollNumber(requestDto.getRollNumber());
//        student.setDateOfBirth(requestDto.getDateOfBirth());
//        student.setGender(requestDto.getGender());
//        student.setBloodGroup(requestDto.getBloodGroup());
//
//        student.setUpdatedAt(LocalDateTime.now());
//        Student updatedStudent = studentRepository.save(student);
//        return StudentResponseDto.builder()
//                .id(updatedStudent.getId())
//                .name(updatedStudent.getName())
//                .admissionNumber(updatedStudent.getAdmissionNumber())
//                .rollNumber(updatedStudent.getRollNumber())
//                .dateOfBirth(updatedStudent.getDateOfBirth())
//                .gender(updatedStudent.getGender())
//                .bloodGroup(updatedStudent.getBloodGroup())
//                .build();
//    }
//
//    public String deleteStudent(Long id){
//        Student student = studentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
//        studentRepository.delete(student);
//        return "Student deleted successfully with id: " + id;
//    }
//
//    public List<StudentResponseDto> getAllStudents() {
//
//        List<Student> students = studentRepository.findAll();
//
//        return students.stream().map(student -> StudentResponseDto.builder()
//                .id(student.getId())
//                .name(student.getName())
//                .admissionNumber(student.getAdmissionNumber())
//                .rollNumber(student.getRollNumber())
//                .dateOfBirth(student.getDateOfBirth())
//                .gender(student.getGender())
//                .bloodGroup(student.getBloodGroup())
//                .build()
//        ).collect(Collectors.toList());
//    }
//
//
//    public StudentResponseDto getStudentById(Long id) {
//
//        Student student = studentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
//
//        return StudentResponseDto.builder()
//                .id(student.getId())
//                .name(student.getName())
//                .admissionNumber(student.getAdmissionNumber())
//                .rollNumber(student.getRollNumber())
//                .dateOfBirth(student.getDateOfBirth())
//                .gender(student.getGender())
//                .bloodGroup(student.getBloodGroup())
//                .build();
//    }

}
