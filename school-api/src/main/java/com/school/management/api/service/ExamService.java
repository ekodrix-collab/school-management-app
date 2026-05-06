package com.school.management.api.service;

import com.school.management.api.entity.Exam;
import com.school.management.api.model.requstModel.ExamRequestDto;
import com.school.management.api.model.responseModel.ExamResponseDto;
import com.school.management.api.repository.ExamRepository;
import com.school.management.api.service.authService.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    public ExamResponseDto createExam(ExamRequestDto requestDto) {
        if (examRepository.findByExamId(requestDto.getExamId()).isPresent()) {
            throw new RuntimeException("Exam with ID " + requestDto.getExamId() + " already exists.");
        }

        String schoolId = AuthUtil.getCurrentSchoolId();

        Exam exam = new Exam();
        exam.setSchoolId(schoolId);
        exam.setExamId(requestDto.getExamId());
        exam.setName(requestDto.getName());
        exam.setType(requestDto.getType());
        exam.setAcademicYearId(requestDto.getAcademicYearId());

        Exam savedExam = examRepository.save(exam);
        return mapToResponseDto(savedExam);
    }

    public ExamResponseDto updateExam(String examId, ExamRequestDto requestDto) {
        Exam exam = examRepository.findByExamId(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        exam.setName(requestDto.getName());
        exam.setType(requestDto.getType());
        exam.setAcademicYearId(requestDto.getAcademicYearId());

        Exam updatedExam = examRepository.save(exam);
        return mapToResponseDto(updatedExam);
    }

    public String deleteExam(String examId) {
        Exam exam = examRepository.findByExamId(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        examRepository.delete(exam);
        return "Exam with ID " + examId + " deleted successfully.";
    }

    public List<ExamResponseDto> getAllExams() {
        return examRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public ExamResponseDto getExamById(String examId) {
        Exam exam = examRepository.findByExamId(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));
        return mapToResponseDto(exam);
    }

    private ExamResponseDto mapToResponseDto(Exam exam) {
        ExamResponseDto dto = new ExamResponseDto();
        dto.setId(exam.getId());
        dto.setExamId(exam.getExamId());
        dto.setName(exam.getName());
        dto.setType(exam.getType());
        dto.setAcademicYearId(exam.getAcademicYearId());
        return dto;
    }
}
