package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.Student;
import com.school.management.api.model.requstModel.StudentRequestDto;
import com.school.management.api.model.responseModel.StudentResponseDto;
import com.school.management.api.repository.StudentRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import com.school.management.api.service.mapper.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    MapperService mapperService;

    public StudentResponseDto createStudent(StudentRequestDto requestDto) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        Student student = new Student();

        student.setName(requestDto.getFirstName() + " " + requestDto.getLastName());
        student.setStudentId(schoolId + "-" + IdGenerator.generateStudentId("S"));
        student.setSchoolId(schoolId);
        student.setParentId(requestDto.getParentId());
        student.setFirstLanguage(requestDto.getFirstLanguage());
        student.setSecondLanguage(requestDto.getSecondLanguage());
        student.setAdmissionNumber(requestDto.getAdmissionNumber());
        if(requestDto.getAdharNo() != null){
            student.setAdharNo(requestDto.getAdharNo());
        }
        student.setDateOfBirth(requestDto.getDateOfBirth());
        student.setGender(requestDto.getGender());
        student.setBloodGroup(requestDto.getBloodGroup());
        student.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        student.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        Student savedStudent = studentRepository.save(student);
        return mapperService.toCreateStudent(savedStudent);

    }


}
