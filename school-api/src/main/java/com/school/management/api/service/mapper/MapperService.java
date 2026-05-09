package com.school.management.api.service.mapper;

import com.school.management.api.entity.*;
import com.school.management.api.model.responseModel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class MapperService {

    @Autowired
    ObjectMapper objectMapper;

    public UserResponse toUserResponse(User savedUser) {
        UserResponse userResponse = new UserResponse();
        userResponse.setMail(savedUser.getEmail());
        userResponse.setName(savedUser.getName());
        userResponse.setRole(savedUser.getRole());
        userResponse.setNumber(savedUser.getMobile());
        userResponse.setUserId(savedUser.getUserId());
        userResponse.setSchoolId(savedUser.getSchoolId());

        return userResponse;
    }

    public StudentResponseDto toCreateStudent(Student student) {
        if (student == null) return null;
        StudentResponseDto dto = new StudentResponseDto();
        dto.setName(student.getName());
        dto.setStudentId(student.getStudentId());
        dto.setAdmissionNumber(student.getAdmissionNumber());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setGender(student.getGender());
        dto.setBloodGroup(student.getBloodGroup());
        dto.setFirstLanguage(student.getFirstLanguage());
        dto.setSecondLanguage(student.getSecondLanguage());

        return dto;
    }

    public AdmissionResponse createAdmissionResponse(StudentResponseDto newStudent, SchoolClassResponse schoolClass, AcademicYearResponseDto academicYear, Admission admission) {
        AdmissionResponse response = new AdmissionResponse();
        response.setName(newStudent.getName());
        response.setClassName(schoolClass.getDisplayName());
        response.setAdmissionNumber(newStudent.getAdmissionNumber());
        response.setAdmissionId(admission.getAdmissionId());
        response.setGender(newStudent.getGender());
        response.setFirstLanguage(newStudent.getFirstLanguage());
        response.setSecondLanguage(newStudent.getSecondLanguage());
        response.setStatus(admission.getStatus());

        return response;
    }

    public SchoolResponse toSchoolResponse(School savedSchool,AddressResponse address) {
        SchoolResponse schoolResponse = new SchoolResponse();
        schoolResponse.setSchoolId(savedSchool.getSchoolId());
        schoolResponse.setEmail(savedSchool.getEmail());
        schoolResponse.setPhone(savedSchool.getPhone());
        schoolResponse.setAddress(address);

        return schoolResponse;

    }

    public TeacherResponseDto toTeacherResponseDto(Teacher teacher) {
        TeacherResponseDto response = new TeacherResponseDto();
        response.setTeacherId(teacher.getTeacherId());
        response.setName(teacher.getName());
        response.setEmail(teacher.getEmail());
        response.setMobile(teacher.getMobile());
        response.setIsActive(teacher.getIsActive());
        response.setRole(teacher.getRole());

        return response;
    }

    public List<TeacherResponseDto> toTeacherResponseDtoList(List<Teacher> teachers) {
        return teachers.stream().map(this::toTeacherResponseDto).toList();
    }

    public OnBoardResponse toTeacherResponse(Teacher teacher) {

        OnBoardResponse response = new OnBoardResponse();
        response.setSuccess(true);
        response.setTeacherId(teacher.getTeacherId());
        response.setMessage("Teacher onboarded successfully");
        response.setName(teacher.getName());
        response.setRole(teacher.getRole());
        response.setIsFirstLogin(teacher.getIsFirstLogin());
        return response;
    }

    public static UUID generateUserId() {
        return UUID.randomUUID();
    }

    public ParentResponse toParentResponse(Parent saved) {
        ParentResponse response = new ParentResponse();
        response.setName(saved.getName());
        response.setIsActive(saved.getIsActive());
        response.setParentId(saved.getParentId());
        return response;

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

    public ClassSubjectResponse toCreateClassSubject(Subject subject, SchoolClass schoolClass, AcademicYear academicYear, ClassSubject classSubject) {
        ClassSubjectResponse classSubjectResponse = new ClassSubjectResponse();

        classSubjectResponse.setSchoolId(subject.getSchoolId());
        classSubjectResponse.setClassName(schoolClass.getStandard() + "-" + schoolClass.getDivision());
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


    public AddressResponse toCreateAddress(Address address) {
        if (address == null) return null;

        AddressResponse response = new AddressResponse();

        response.setAddressId(address.getAddressId());
        response.setName(address.getName());
        response.setPlace(address.getPlace());
        response.setCity(address.getCity());
        response.setDistrict(address.getDistrict());
        response.setState(address.getState());
        response.setPincode(address.getPincode());
        response.setLandmark(address.getLandmark());

        return response;
    }


}