package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.Teacher;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.OnboardRequest;
import com.school.management.api.model.responseModel.OnBoardResponse;
import com.school.management.api.repository.TeacherRepository;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MapperService mapperService;

    @Transactional
    public OnBoardResponse createTeacher(OnboardRequest request) {

        Teacher teacher = new Teacher();
        teacher.setName(request.getName());
        teacher.setEmail(request.getEmail());
        teacher.setMobile(request.getMobile());
        teacher.setRole(request.getRole());
        teacher.setIsActive(true);
        teacher.setIsFirstLogin(true);
        teacher.setSchoolId(request.getSchoolId());
        teacher.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        teacher.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        try {
            if (request.getSubject() != null && !request.getSubject().isEmpty()) {
                String subject = objectMapper.writeValueAsString(request.getSubject());
                teacher.setSubject(subject);
            }

            if (request.getClassIds() != null && !request.getClassIds().isEmpty()) {
                String classIds = objectMapper.writeValueAsString(request.getClassIds());
                teacher.setClassId(classIds);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize teacher fields", e);
        }

        Teacher saveTeacher = teacherRepository.save(teacher);

        return mapperService.toTeacherResponse(saveTeacher);

    }


}
