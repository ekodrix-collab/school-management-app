package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.Teacher;
import com.school.management.api.entity.User;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.OnboardRequest;
import com.school.management.api.model.responseModel.OnBoardResponse;
import com.school.management.api.repository.TeacherRepository;
import com.school.management.api.repository.UserRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MapperService mapperService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public OnBoardResponse createTeacher(OnboardRequest request) {

        UUID userId = AuthUtil.getCurrentUserId();
        if(userId == null){
            throw new BadRequestException("Admin can only create admission");
        }
        User users = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

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

        User user = new User();
        user.setName(request.getName());
        user.setSchoolId(users.getSchoolId());
        user.setMobile(request.getMobile());
        user.setUserId(MapperService.generateUserId());
        user.setPassword(passwordEncoder.encode(Constants.DUMMY_PASSWORD));
        user.setRole(request.getRole());
        user.setEmail(request.getEmail());
        user.setIsFirstLogin(true);
        user.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        user.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        User savedUser = userRepository.save(user);
        teacher.setTeacherId(savedUser.getUserId());

        Teacher saveTeacher = teacherRepository.save(teacher);

        return mapperService.toTeacherResponse(saveTeacher);

    }


}
