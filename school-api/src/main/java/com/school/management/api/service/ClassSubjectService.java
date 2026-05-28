package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.AcademicYear;
import com.school.management.api.entity.ClassSubject;
import com.school.management.api.entity.SchoolClass;
import com.school.management.api.entity.Subject;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.exception.ResourceNotFoundException;
import com.school.management.api.model.requstModel.ClassSubjectRequest;
import com.school.management.api.model.responseModel.ClassSubjectResponse;
import com.school.management.api.repository.*;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import com.school.management.api.service.mapper.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ClassSubjectService {

    @Autowired
    ClassSubjectRepository classSubjectRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SchoolClassRepository schoolClassRepository;

    @Autowired
    AcademicYearRepository academicYearRepository;

    @Autowired
    MapperService mapperService;


    public ClassSubjectResponse createClassSubject(ClassSubjectRequest request) {

        Subject subject = null;
        SchoolClass schoolClass = null;
        AcademicYear academicYear = null;

        String schoolId = AuthUtil.getCurrentSchoolId();
        String role = AuthUtil.getCurrentRole();

        if (!Constants.ROLE_ADMIN.equalsIgnoreCase(role)) {
            throw new BadRequestException("Only admin can assign subjects to classes");
        }

        if (request.getSubjectId() != null) {
            subject = subjectRepository.findBySubjectId(request.getSubjectId())
                    .orElseThrow(() -> new BadRequestException("Subject not found"));
        }
        if (request.getClassId() != null) {
            schoolClass = schoolClassRepository.findByClassId(request.getClassId())
                    .orElseThrow(() -> new BadRequestException("Class not found"));
        }

        if (request.getAcademicYearId() != null) {
            academicYear = academicYearRepository.findByAcademicYearId(request.getAcademicYearId())
                    .orElseThrow(() -> new BadRequestException("Academic year not found"));
        }

        ClassSubject classSubject = new ClassSubject();

        classSubject.setSchoolId(schoolId);
        classSubject.setClassSubjectId(IdGenerator.generateStudentId("CSI"));
        classSubject.setSubjectId(subject.getSubjectId());
        classSubject.setClassId(schoolClass.getClassId());
        classSubject.setAcademicYearId(academicYear.getAcademicYearId());
        classSubject.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        classSubject.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        try {
            classSubjectRepository.save(classSubject);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(schoolClass.getStandard() + "-" + schoolClass.getDivision() + " class already added " + subject.getName());
        }
        return mapperService.toCreateClassSubject(subject, schoolClass, academicYear, classSubject);
    }

    public List<ClassSubjectResponse> getAllClassSubject() {
        String schoolId = AuthUtil.getCurrentSchoolId();

        List<ClassSubject> classSubjects = classSubjectRepository.getAllClassSubject(schoolId);

        List<String> subjectIds = classSubjects.stream()
                .map(ClassSubject::getSubjectId)
                .distinct()
                .toList();

        List<String> classIds = classSubjects.stream()
                .map(ClassSubject::getClassId)
                .distinct()
                .toList();

        List<String> academicYearIds = classSubjects.stream()
                .map(ClassSubject::getAcademicYearId)
                .distinct()
                .toList();

        Map<String, Subject> subjectMap = subjectRepository
                .findAllBySubjectIdIn(subjectIds)
                .stream()
                .collect(Collectors.toMap(
                        Subject::getSubjectId,
                        Function.identity()
                ));

        Map<String, SchoolClass> classMap = schoolClassRepository
                .findAllByClassIdIn(classIds)
                .stream()
                .collect(Collectors.toMap(
                        SchoolClass::getClassId,
                        Function.identity()
                ));

        Map<String, AcademicYear> academicYearMap = academicYearRepository
                .findAllByAcademicYearIdIn(academicYearIds)
                .stream()
                .collect(Collectors.toMap(
                        AcademicYear::getAcademicYearId,
                        Function.identity()
                ));

        return classSubjects.stream()
                .map(classSubject -> {
                    Subject subject = subjectMap.get(classSubject.getSubjectId());
                    SchoolClass schoolClass = classMap.get(classSubject.getClassId());
                    AcademicYear academicYear = academicYearMap.get(classSubject.getAcademicYearId());
                    return mapperService.toCreateClassSubject(
                            subject,
                            schoolClass,
                            academicYear,
                            classSubject);})
                .toList();
    }

    public ClassSubjectResponse getClassSubjectResponseById(String classSubjectId) {

        ClassSubject classSubject = classSubjectRepository.findByClassSubjectId(classSubjectId);
        if(classSubject == null){
            throw new ResourceNotFoundException("Class Subject ID Not found");
        }
        Subject subject = subjectRepository.getBySubjectId(classSubject.getSubjectId());
        AcademicYear academicYear = academicYearRepository.getAcademicYear(classSubject.getAcademicYearId());
        SchoolClass schoolClass = schoolClassRepository.getSchoolClass(classSubject.getClassId());

        return mapperService.toCreateClassSubject(subject,schoolClass,academicYear,classSubject);
    }

    @Transactional
    public ClassSubjectResponse updateClassSubject(String classSubjectId, ClassSubjectRequest request) {
        String role = AuthUtil.getCurrentRole();
        if (!Constants.ROLE_ADMIN.equalsIgnoreCase(role)) {
            throw new BadRequestException("Only admin can assign subjects to classes");
        }

        ClassSubject classSubject = classSubjectRepository.findByClassSubjectId(classSubjectId);
        if (classSubject == null) {
            throw new ResourceNotFoundException("Class Subject ID Not found");
        }

        String schoolId = AuthUtil.getCurrentSchoolId();
        if (!classSubject.getSchoolId().equals(schoolId)) {
            throw new BadRequestException("Class Subject assignment does not belong to this school");
        }

        Subject subject = null;
        SchoolClass schoolClass = null;
        AcademicYear academicYear = null;

        if (request.getSubjectId() != null) {
            subject = subjectRepository.findBySubjectId(request.getSubjectId())
                    .orElseThrow(() -> new BadRequestException("Subject not found"));
        } else {
            subject = subjectRepository.findBySubjectId(classSubject.getSubjectId())
                    .orElseThrow(() -> new BadRequestException("Subject not found"));
        }

        if (request.getClassId() != null) {
            schoolClass = schoolClassRepository.findByClassId(request.getClassId())
                    .orElseThrow(() -> new BadRequestException("Class not found"));
        } else {
            schoolClass = schoolClassRepository.findByClassId(classSubject.getClassId())
                    .orElseThrow(() -> new BadRequestException("Class not found"));
        }

        if (request.getAcademicYearId() != null) {
            academicYear = academicYearRepository.findByAcademicYearId(request.getAcademicYearId())
                    .orElseThrow(() -> new BadRequestException("Academic year not found"));
        } else {
            academicYear = academicYearRepository.findByAcademicYearId(classSubject.getAcademicYearId())
                    .orElseThrow(() -> new BadRequestException("Academic year not found"));
        }

        classSubject.setSubjectId(subject.getSubjectId());
        classSubject.setClassId(schoolClass.getClassId());
        classSubject.setAcademicYearId(academicYear.getAcademicYearId());
        classSubject.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        try {
            classSubjectRepository.save(classSubject);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(schoolClass.getStandard() + "-" + schoolClass.getDivision() + " class already added " + subject.getName());
        }

        return mapperService.toCreateClassSubject(subject, schoolClass, academicYear, classSubject);
    }

    @Transactional
    public String deleteClassSubject(String classSubjectId) {
        String role = AuthUtil.getCurrentRole();
        if (!Constants.ROLE_ADMIN.equalsIgnoreCase(role)) {
            throw new BadRequestException("Only admin can assign subjects to classes");
        }

        ClassSubject classSubject = classSubjectRepository.findByClassSubjectId(classSubjectId);
        if (classSubject == null) {
            throw new ResourceNotFoundException("Class Subject ID Not found");
        }

        String schoolId = AuthUtil.getCurrentSchoolId();
        if (!classSubject.getSchoolId().equals(schoolId)) {
            throw new BadRequestException("Class Subject assignment does not belong to this school");
        }

        classSubjectRepository.delete(classSubject);
        return "Class Subject assignment deleted successfully";
    }

}
