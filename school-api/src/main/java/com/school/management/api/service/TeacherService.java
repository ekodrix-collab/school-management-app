package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.Teacher;
import com.school.management.api.entity.User;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.OnboardRequest;
import com.school.management.api.model.responseModel.OnBoardResponse;
import com.school.management.api.model.responseModel.SchoolTotalCount;
import com.school.management.api.model.responseModel.TeacherTotalCount;
import com.school.management.api.repository.TeacherRepository;
import com.school.management.api.repository.UserRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.school.management.api.model.responseModel.TeacherResponseDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    MapperService mapperService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public OnBoardResponse createTeacher(OnboardRequest request) {

        String userRole = AuthUtil.getCurrentRole();
        if (!Constants.ROLE_ADMIN.equalsIgnoreCase(userRole)) {
            throw new BadRequestException("Only admin can create Teacher");
        }
        String schoolId = AuthUtil.getCurrentSchoolId();

        Teacher teacher = new Teacher();
        teacher.setName(request.getName());
        teacher.setEmail(request.getEmail());
        teacher.setMobile(request.getMobile());
        teacher.setRole(request.getRole());
        teacher.setIsActive(true);
        teacher.setIsFirstLogin(true);
        teacher.setSchoolId(schoolId);
        teacher.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        teacher.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        User user = new User();
        user.setName(request.getName());
        user.setSchoolId(schoolId);
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

    public List<TeacherResponseDto> getAllTeachers() {
        String schoolId = AuthUtil.getCurrentSchoolId();
        List<Teacher> teachers = teacherRepository.findAllTeachers(schoolId);
        return mapperService.toTeacherResponseDtoList(teachers);
    }

    public TeacherResponseDto getTeacherByTeacherId(UUID teacherId) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId)
                .orElseThrow(() -> new BadRequestException("Teacher not found"));
        return mapperService.toTeacherResponseDto(teacher);
    }

    @Transactional
    public TeacherResponseDto updateTeacher(UUID teacherId, OnboardRequest request) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId)
                .orElseThrow(() -> new BadRequestException("Teacher not found"));

        teacher.setName(request.getName());
        teacher.setEmail(request.getEmail());
        teacher.setMobile(request.getMobile());
        teacher.setRole(request.getRole());
        teacher.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        User user = userRepository.findByUserId(teacherId)
                .orElseThrow(() -> new BadRequestException("User record not found for teacher"));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setRole(request.getRole());
        user.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        userRepository.save(user);

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return mapperService.toTeacherResponseDto(updatedTeacher);
    }

    @Transactional
    public String deleteTeacher(UUID teacherId) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId)
                .orElseThrow(() -> new BadRequestException("Teacher not found"));

        User user = userRepository.findByUserId(teacherId)
                .orElseThrow(() -> new BadRequestException("User record not found for teacher"));
        userRepository.delete(user);

        teacherRepository.delete(teacher);
        return "Teacher deleted successfully";
    }

}
