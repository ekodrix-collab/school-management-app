package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.TeacherClassSubjects;
import com.school.management.api.entity.Teacher;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.exception.ResourceNotFoundException;
import com.school.management.api.model.requstModel.TeacherClassSubjectsRequest;
import com.school.management.api.model.responseModel.ClassSubjectResponse;
import com.school.management.api.model.responseModel.TeacherClassSubjectsResponse;
import com.school.management.api.model.responseModel.TeacherResponseDto;
import com.school.management.api.repository.TeacherClassSubjectsRepository;
import com.school.management.api.repository.TeacherRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TeacherClassSubjectsService {

    @Autowired
    TeacherClassSubjectsRepository teacherClassSubjectsRepository;

    @Autowired
    TeacherRepository teacherRepository;

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

    public TeacherClassSubjectsResponse getTeacherClassSubjectById(String teacherClassSubjectId) {
        TeacherClassSubjects teacherClassSubjects = teacherClassSubjectsRepository.findByTeacherClassSubjectId(teacherClassSubjectId);
        if (teacherClassSubjects == null) {
            throw new ResourceNotFoundException("Teacher Class Subject ID Not found");
        }

        String schoolId = AuthUtil.getCurrentSchoolId();
        if (!teacherClassSubjects.getSchoolId().equals(schoolId)) {
            throw new BadRequestException("Teacher Class Subject assignment does not belong to this school");
        }

        TeacherResponseDto teacher = teacherService.getTeacherByTeacherId(teacherClassSubjects.getTeacherId());
        ClassSubjectResponse classSubject = classSubjectService.getClassSubjectResponseById(teacherClassSubjects.getClassSubjectId());

        TeacherClassSubjectsResponse response = new TeacherClassSubjectsResponse();
        response.setTeacherClassSubjectId(teacherClassSubjects.getTeacherClassSubjectId());
        response.setTeacherName(teacher.getName());
        response.setSubjectName(classSubject.getSubject());
        response.setClassName(classSubject.getClassName());
        response.setAcademicYearName(classSubject.getAcademicYearName());

        return response;
    }

    public List<TeacherClassSubjectsResponse> getAllTeacherClassSubjects() {
        String schoolId = AuthUtil.getCurrentSchoolId();
        List<TeacherClassSubjects> list = teacherClassSubjectsRepository.findBySchoolId(schoolId);
        if (list.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        List<Teacher> teachers = teacherRepository.findAllBySchoolId(schoolId);
        Map<UUID, String> teacherMap = teachers.stream()
                .collect(Collectors.toMap(Teacher::getTeacherId, Teacher::getName, (a, b) -> a));

        List<ClassSubjectResponse> classSubjects = classSubjectService.getAllClassSubject();
        Map<String, ClassSubjectResponse> classSubjectMap = classSubjects.stream()
                .collect(Collectors.toMap(ClassSubjectResponse::getClassSubjectId, Function.identity(), (a, b) -> a));

        return list.stream().map(item -> {
            TeacherClassSubjectsResponse response = new TeacherClassSubjectsResponse();
            response.setTeacherClassSubjectId(item.getTeacherClassSubjectId());
            
            String teacherName = teacherMap.getOrDefault(item.getTeacherId(), "Unknown Teacher");
            response.setTeacherName(teacherName);

            ClassSubjectResponse classSubject = classSubjectMap.get(item.getClassSubjectId());
            if (classSubject != null) {
                response.setSubjectName(classSubject.getSubject());
                response.setClassName(classSubject.getClassName());
                response.setAcademicYearName(classSubject.getAcademicYearName());
            } else {
                response.setSubjectName("Unknown Subject");
                response.setClassName("Unknown Class");
                response.setAcademicYearName("Unknown Year");
            }
            return response;
        }).collect(Collectors.toList());
    }

    @Transactional
    public TeacherClassSubjectsResponse updateTeacherClassSubjects(String teacherClassSubjectId, TeacherClassSubjectsRequest request) {
        String role = AuthUtil.getCurrentRole();
        if (!Constants.ROLE_ADMIN.equalsIgnoreCase(role)) {
            throw new BadRequestException("Admin can update teacher class subject");
        }

        TeacherClassSubjects teacherClassSubjects = teacherClassSubjectsRepository.findByTeacherClassSubjectId(teacherClassSubjectId);
        if (teacherClassSubjects == null) {
            throw new ResourceNotFoundException("Teacher Class Subject ID Not found");
        }

        String schoolId = AuthUtil.getCurrentSchoolId();
        if (!teacherClassSubjects.getSchoolId().equals(schoolId)) {
            throw new BadRequestException("Teacher Class Subject assignment does not belong to this school");
        }

        TeacherResponseDto teacher = null;
        ClassSubjectResponse classSubject = null;

        if (request.getTeacherId() != null) {
            teacher = teacherService.getTeacherByTeacherId(request.getTeacherId());
        } else {
            teacher = teacherService.getTeacherByTeacherId(teacherClassSubjects.getTeacherId());
        }

        if (request.getClassSubjectId() != null) {
            classSubject = classSubjectService.getClassSubjectResponseById(request.getClassSubjectId());
        } else {
            classSubject = classSubjectService.getClassSubjectResponseById(teacherClassSubjects.getClassSubjectId());
        }

        teacherClassSubjects.setTeacherId(teacher.getTeacherId());
        teacherClassSubjects.setClassSubjectId(classSubject.getClassSubjectId());
        teacherClassSubjects.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        TeacherClassSubjects savedEntity = teacherClassSubjectsRepository.save(teacherClassSubjects);

        TeacherClassSubjectsResponse response = new TeacherClassSubjectsResponse();
        response.setTeacherClassSubjectId(savedEntity.getTeacherClassSubjectId());
        response.setTeacherName(teacher.getName());
        response.setSubjectName(classSubject.getSubject());
        response.setClassName(classSubject.getClassName());
        response.setAcademicYearName(classSubject.getAcademicYearName());

        return response;
    }

    @Transactional
    public String deleteTeacherClassSubjects(String teacherClassSubjectId) {
        String role = AuthUtil.getCurrentRole();
        if (!Constants.ROLE_ADMIN.equalsIgnoreCase(role)) {
            throw new BadRequestException("Admin can delete teacher class subject");
        }

        TeacherClassSubjects teacherClassSubjects = teacherClassSubjectsRepository.findByTeacherClassSubjectId(teacherClassSubjectId);
        if (teacherClassSubjects == null) {
            throw new ResourceNotFoundException("Teacher Class Subject ID Not found");
        }

        String schoolId = AuthUtil.getCurrentSchoolId();
        if (!teacherClassSubjects.getSchoolId().equals(schoolId)) {
            throw new BadRequestException("Teacher Class Subject assignment does not belong to this school");
        }

        teacherClassSubjectsRepository.delete(teacherClassSubjects);
        return "Teacher Class Subject assignment deleted successfully";
    }
}
