package com.school.management.api.service.mapper;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.*;
import com.school.management.api.model.responseModel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class MapperService {

    @Autowired
    ObjectMapper objectMapper;

    public TeacherResponseDto toTeacherResponseDto(Teacher teacher) {
        TeacherResponseDto response = new TeacherResponseDto();
        response.setTeacherId(teacher.getTeacherId());
        response.setName(teacher.getName());
        response.setEmail(teacher.getEmail());
        response.setMobile(teacher.getMobile());
        response.setIsActive(teacher.getIsActive());
        response.setRole(teacher.getRole());

//        try {
//            if (teacher.getSubject() != null && !teacher.getSubject().isEmpty()) {
//                List<String> subjects = objectMapper.readValue(
//                        teacher.getSubject(), new TypeReference<>() {
//                        });
//                response.setSubject(subjects);
//            }
//            if (teacher.getClassId() != null && !teacher.getClassId().isEmpty()) {
//                List<String> classId = objectMapper.readValue(
//                        teacher.getClassId(), new TypeReference<>() {
//                        });
//                response.setClasses(classId);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to deserialize teacher fields", e);
//        }
        return response;
    }

    public List<TeacherResponseDto> toTeacherResponseDtoList(List<Teacher> teachers) {
        return teachers.stream().map(this::toTeacherResponseDto).toList();
    }

    public OnBoardResponse toTeacherResponse(Teacher teacher) {

        OnBoardResponse response = new OnBoardResponse();

        response.setSuccess(true);
        response.setMessage("Teacher onboarded successfully");
        response.setName(teacher.getName());
        response.setRole(teacher.getRole());
        response.setIsFirstLogin(teacher.getIsFirstLogin());

//        try {
//            if (teacher.getSubject() != null && !teacher.getSubject().isEmpty()) {
//                List<String> subjects = objectMapper.readValue(
//                        teacher.getSubject(), new TypeReference<>() {
//                        });
//                response.setSubject(subjects);
//            }
//            if (teacher.getClassId() != null && !teacher.getClassId().isEmpty()) {
//                List<String> classId = objectMapper.readValue(
//                        teacher.getClassId(), new TypeReference<>() {
//                        });
//                response.setClasses(classId);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to deserialize teacher fields", e);
//        }
        return response;

    }

    public SchoolResponse toSchoolResponse(School savedSchool, User savedUser) {

        SchoolResponse schoolResponse = new SchoolResponse();
        schoolResponse.setSchoolName(savedSchool.getSchoolName());
        schoolResponse.setSchoolCode(savedSchool.getSchoolId());

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

    public UserResponse toUserResponse(User savedUser) {
        UserResponse userResponse = new UserResponse();
        userResponse.setMail(savedUser.getEmail());
        userResponse.setName(savedUser.getName());
        userResponse.setRole(savedUser.getRole());
        userResponse.setNumber(savedUser.getMobile());
        return userResponse;

    }

    public static UUID generateUserId() {
        return UUID.randomUUID();
    }

    public ParentResponse toParentResponse(Parent saved) {
        ParentResponse response = new ParentResponse();
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        response.setIsActive(saved.getIsActive());
        response.setRole(saved.getRole());
        response.setParentId(saved.getParentId());
        return response;

    }

    public StudentResponseDto toCreateStudent(Student student) {

        if (student == null) {
            return null;
        }

        StudentResponseDto dto = new StudentResponseDto();
        dto.setName(student.getName());
        dto.setAdmissionNumber(student.getAdmissionNumber());
        dto.setRollNumber(student.getRollNumber());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setGender(student.getGender());
        dto.setBloodGroup(student.getBloodGroup());
        return dto;
    }

    public AcademicYearResponseDto toAcademicYearResponseDto(AcademicYear academicYear) {
        if (academicYear == null) {
            return null;
        }

        return AcademicYearResponseDto.builder()
                .academicYearId(academicYear.getAcademicYearId())
                .schoolId(academicYear.getSchoolId())
                .name(academicYear.getName())
                .startDate(academicYear.getStartDate())
                .endDate(academicYear.getEndDate())
                .status(academicYear.getStatus())
                .createdAt(academicYear.getCreatedAt())
                .updatedAt(academicYear.getUpdatedAt())
                .build();
    }

    public List<AcademicYearResponseDto> toAcademicYearResponseDtoList(List<AcademicYear> academicYears) {
        return academicYears.stream()
                .map(this::toAcademicYearResponseDto)
                .toList();
    }

    public ClassSubjectResponse toCreateClassSubject(Subject subject, SchoolClass schoolClass, AcademicYear academicYear,ClassSubject classSubject) {
        ClassSubjectResponse classSubjectResponse = new ClassSubjectResponse();

        classSubjectResponse.setSchoolId(subject.getSchoolId());
        classSubjectResponse.setClassName(schoolClass.getStandard() + "-" +schoolClass.getDivision());
        classSubjectResponse.setAcademicYearName(academicYear.getName());
        classSubjectResponse.setClassSubjectId(classSubject.getClassSubjectId());
        classSubjectResponse.setSubject(subject.getName());

        return classSubjectResponse;

    }

    public String buildAcademicYear(LocalDate startDate, LocalDate endDate) {

        String start = startDate.getMonth()
                        .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                        .toLowerCase()
                        + "_" + startDate.getYear();

        String end = endDate.getMonth()
                        .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                        .toLowerCase()
                        + "_" + endDate.getYear();

        return start + "-" + end;
    }


}