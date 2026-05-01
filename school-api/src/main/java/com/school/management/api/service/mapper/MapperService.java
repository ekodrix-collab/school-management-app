package com.school.management.api.service.mapper;


import com.school.management.api.entity.Teacher;
import com.school.management.api.model.responseModel.OnBoardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class MapperService {

    @Autowired
    ObjectMapper objectMapper;

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
}