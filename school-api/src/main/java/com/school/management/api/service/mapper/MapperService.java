package com.school.management.api.service.mapper;


import com.school.management.api.constants.Constants;
import com.school.management.api.entity.School;
import com.school.management.api.entity.Teacher;
import com.school.management.api.entity.User;
import com.school.management.api.model.responseModel.AdminResponse;
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

        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setMail(savedUser.getEmail());
        adminResponse.setName(savedUser.getName());
        adminResponse.setRole(savedUser.getRole());
        adminResponse.setNumber(savedUser.getMobile());

        schoolResponse.setAdmin(adminResponse);

        return schoolResponse;
    }

    public static String getAcademicYear() {
        int year = LocalDate.now(ZoneId.of(Constants.INDIAN_TIME)).getYear();
        return year + "-" + (year + 1);
    }
}