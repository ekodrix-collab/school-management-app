package com.school.management.api.service;


import com.school.management.api.entity.ExamSubject;
import com.school.management.api.model.requstModel.ExamSubjectRequest;
import com.school.management.api.model.responseModel.ExamSubjectResponse;
import com.school.management.api.repository.ExamSubjectRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamSubjectService {

    private final ExamSubjectRepository examSubjectRepository;

    public ExamSubjectResponse createExamSubject(ExamSubjectRequest request) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        Boolean alreadyExists = examSubjectRepository.existsByExamIdAndClassIdAndClassSubjectIdAndSchoolId(
                request.getExamId(),
                request.getClassId(),
                request.getClassSubjectId(),
                schoolId);

        if (alreadyExists) {
            throw new RuntimeException("Exam subject already exists");
        }

        if (request.getPassMark() > request.getMaxMark()) {
            throw new RuntimeException("Pass mark cannot be greater than max mark");
        }

        ExamSubject examSubject = new ExamSubject();

        examSubject.setExamSubjectId(IdGenerator.generateStudentId("EX-SUB"));
        examSubject.setSchoolId(schoolId);
        examSubject.setAcademicYearId(request.getAcademicYearId());
        examSubject.setExamId(request.getExamId());
        examSubject.setClassId(request.getClassId());
        examSubject.setClassSubjectId(request.getClassSubjectId());
        examSubject.setExamDate(request.getExamDate());
        examSubject.setStartTime(request.getStartTime());
        examSubject.setEndTime(request.getEndTime());
        examSubject.setMaxMark(request.getMaxMark());
        examSubject.setPassMark(request.getPassMark());
        examSubject.setRemarks(request.getRemarks());
        examSubject.setStatus("SCHEDULED");
        examSubject.setIsActive(true);
        examSubject.setCreatedAt(LocalDateTime.now());
        examSubject.setUpdatedAt(LocalDateTime.now());

        ExamSubject savedExamSubject = examSubjectRepository.save(examSubject);
        return mapToResponse(savedExamSubject);

    }

    public List<ExamSubjectResponse> getExamSubjects(String examId, String classId) {

        List<ExamSubject> examSubjects = examSubjectRepository.findByExamIdAndClassId(examId, classId);
        return examSubjects.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ExamSubjectResponse mapToResponse(ExamSubject examSubject) {

        ExamSubjectResponse response = new ExamSubjectResponse();

        response.setExamSubjectId(examSubject.getExamSubjectId());
        response.setSchoolId(examSubject.getSchoolId());
        response.setAcademicYearId(examSubject.getAcademicYearId());
        response.setExamId(examSubject.getExamId());
        response.setClassId(examSubject.getClassId());
        response.setClassSubjectId(examSubject.getClassSubjectId());
        response.setExamDate(examSubject.getExamDate());
        response.setStartTime(examSubject.getStartTime());
        response.setEndTime(examSubject.getEndTime());
        response.setMaxMark(examSubject.getMaxMark());
        response.setPassMark(examSubject.getPassMark());
        response.setStatus(examSubject.getStatus());
        response.setRemarks(examSubject.getRemarks());
        response.setIsActive(examSubject.getIsActive());
        response.setCreatedAt(examSubject.getCreatedAt());

        return response;
    }

}