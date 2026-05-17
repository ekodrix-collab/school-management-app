package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.TeacherClassSubjects;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.TeacherClassSubjectsRequest;
import com.school.management.api.model.responseModel.ClassSubjectResponse;
import com.school.management.api.model.responseModel.TeacherClassSubjectsResponse;
import com.school.management.api.model.responseModel.TeacherResponseDto;
import com.school.management.api.repository.TeacherClassSubjectsRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TeacherClassSubjectsService {

    @Autowired
    TeacherClassSubjectsRepository teacherClassSubjectsRepository;

    @Autowired
    TeacherService teacherService;

    @Autowired
    ClassSubjectService classSubjectService;

    public TeacherClassSubjectsResponse createTeacherClassSubjects(TeacherClassSubjectsRequest request) {

        String role = AuthUtil.getCurrentRole();
        if (!Constants.ROLE_ADMIN.equalsIgnoreCase(role)) {
            throw new BadRequestException("Admin can create teacher class subject");
        }

        TeacherResponseDto teacher = null;
        ClassSubjectResponse classSubject = null;

        if (request.getTeacherId() != null) {
            teacher = teacherService.getTeacherByTeacherId(request.getTeacherId());
        }

        if (request.getClassSubjectId() != null) {
            classSubject = classSubjectService.getClassSubjectResponseById(request.getClassSubjectId());
        }

        String schoolId = AuthUtil.getCurrentSchoolId();

        TeacherClassSubjects teacherClassSubjects = new TeacherClassSubjects();
        teacherClassSubjects.setTeacherId(teacher.getTeacherId());
        teacherClassSubjects.setTeacherClassSubjectId(IdGenerator.generateStudentId("TCS"));
        teacherClassSubjects.setSchoolId(schoolId);
        teacherClassSubjects.setClassSubjectId(classSubject.getClassSubjectId());
        teacherClassSubjects.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        teacherClassSubjects.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        TeacherClassSubjects saveTeacherClassSubject = teacherClassSubjectsRepository.save(teacherClassSubjects);

        TeacherClassSubjectsResponse teacherClassSubjectsResponse = new TeacherClassSubjectsResponse();

        teacherClassSubjectsResponse.setSubjectName(classSubject.getSubject());
        teacherClassSubjectsResponse.setTeacherName(teacher.getName());
        teacherClassSubjectsResponse.setClassName(classSubject.getClassName());
        teacherClassSubjectsResponse.setAcademicYearName(classSubject.getAcademicYearName());
        teacherClassSubjectsResponse.setTeacherClassSubjectId(saveTeacherClassSubject.getTeacherClassSubjectId());

        return teacherClassSubjectsResponse;

    }
}
