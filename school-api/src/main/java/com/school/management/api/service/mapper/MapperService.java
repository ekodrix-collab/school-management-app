package com.school.management.api.service.mapper;


import com.school.management.api.constants.Constants;
import com.school.management.api.entity.Parent;
import com.school.management.api.entity.School;
import com.school.management.api.entity.Teacher;
import com.school.management.api.entity.User;
import com.school.management.api.model.responseModel.ParentResponse;
import com.school.management.api.model.responseModel.UserResponse;
import com.school.management.api.model.responseModel.OnBoardResponse;
import com.school.management.api.model.responseModel.SchoolResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class MapperService {

    @Autowired
    ObjectMapper objectMapper;

    public static UUID generateUserId() {
        return UUID.randomUUID();
    }

    public OnBoardResponse toTeacherResponse(Teacher teacher) {

        OnBoardResponse response = new OnBoardResponse();

        response.setSuccess(true);
        response.setMessage("Teacher onboarded successfully");
        response.setName(teacher.getName());
        response.setRole(teacher.getRole());
        response.setIsFirstLogin(teacher.getIsFirstLogin());

        try {
            if (teacher.getSubject() != null && !teacher.getSubject().isEmpty()) {
                List<String> subjects = objectMapper.readValue(
                        teacher.getSubject(), new TypeReference<>() {
                        });
                response.setSubject(subjects);
            }
            if(teacher.getClassId() != null && !teacher.getClassId().isEmpty()){
                List<String> classId = objectMapper.readValue(
                        teacher.getClassId(), new TypeReference<>() {
                        });
                response.setClasses(classId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize teacher fields", e);
        }

        return response;
    }

    public SchoolResponse toSchoolResponse(School savedSchool, User savedUser) {

        SchoolResponse schoolResponse = new SchoolResponse();
        schoolResponse.setSchoolName(savedSchool.getSchoolName());
        schoolResponse.setSchoolCode(savedSchool.getSchoolCode());

        UserResponse userResponse = new UserResponse();
        userResponse.setMail(savedUser.getEmail());
        userResponse.setName(savedUser.getName());
        userResponse.setRole(savedUser.getRole());
        userResponse.setNumber(savedUser.getMobile());

        schoolResponse.setAdmin(userResponse);

        return schoolResponse;
    }

    public static String getAcademicYear() {
        LocalDate today = LocalDate.now(ZoneId.of(Constants.INDIAN_TIME));
        int year = today.getYear();
        int month = today.getMonthValue();

        if (month < 6) {
            return (year - 1) + "-" + year;
        } else {
            return year + "-" + (year + 1);
        }
    }
    public UserResponse toUserResponse(User savedUser){
        UserResponse userResponse = new UserResponse();
        userResponse.setMail(savedUser.getEmail());
        userResponse.setName(savedUser.getName());
        userResponse.setRole(savedUser.getRole());
        userResponse.setNumber(savedUser.getMobile());
        return userResponse;

    }


    public ParentResponse toParentResponse(Parent saved) {

        ParentResponse response = new ParentResponse();
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        response.setIsActive(saved.getIsActive());
        response.setRole(saved.getRole());

        if (saved.getStudentIds() != null && !saved.getStudentIds().isEmpty()) {
            List<String> subjects = objectMapper.readValue(
                    saved.getStudentIds(), new TypeReference<>() {
                    });
            response.setStudents(subjects);
        }

        return response;
    }
}