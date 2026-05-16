package com.school.management.api.service;


import com.school.management.api.entity.Exam;
import com.school.management.api.model.requstModel.ExamRequest;
import com.school.management.api.model.responseModel.ExamResponse;
import com.school.management.api.repository.ExamRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    public ExamResponse createExam(ExamRequest request) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        Boolean alreadyExists = examRepository.existsBySchoolIdAndAcademicYearIdAndExamName(
                        schoolId,
                        request.getAcademicYearId(),
                        request.getExamName());

        if (alreadyExists) {
            throw new RuntimeException("Exam already exists");
        }

        Exam exam = new Exam();

        exam.setExamId(IdGenerator.generateStudentId("EXAM"));
        exam.setSchoolId(schoolId);
        exam.setAcademicYearId(request.getAcademicYearId());
        exam.setExamName(request.getExamName());
        exam.setExamType(request.getExamType());
        exam.setStartDate(request.getStartDate());
        exam.setEndDate(request.getEndDate());
        exam.setResultPublishDate(request.getResultPublishDate());
        exam.setRemarks(request.getRemarks());
        exam.setStatus("DRAFT");
        exam.setIsActive(true);
        exam.setCreatedAt(LocalDateTime.now());
        exam.setUpdatedAt(LocalDateTime.now());

        Exam savedExam = examRepository.save(exam);
        return mapToResponse(savedExam);
    }

    public List<ExamResponse> getAllExams(String schoolId, String academicYearId) {

        List<Exam> exams = examRepository.findBySchoolIdAndAcademicYearId(
                        schoolId,
                        academicYearId);

        return exams.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ExamResponse mapToResponse(Exam exam) {

        ExamResponse response = new ExamResponse();

        response.setExamId(exam.getExamId());
        response.setSchoolId(exam.getSchoolId());
        response.setAcademicYearId(exam.getAcademicYearId());
        response.setExamName(exam.getExamName());
        response.setExamType(exam.getExamType());
        response.setStartDate(exam.getStartDate());
        response.setEndDate(exam.getEndDate());
        response.setResultPublishDate(exam.getResultPublishDate());
        response.setStatus(exam.getStatus());
        response.setRemarks(exam.getRemarks());
        response.setIsActive(exam.getIsActive());
        response.setCreatedAt(exam.getCreatedAt());

        return response;
    }

}