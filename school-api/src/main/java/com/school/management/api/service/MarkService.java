package com.school.management.api.service;

import com.school.management.api.entity.Mark;
import com.school.management.api.model.requstModel.MarkRequestDto;
import com.school.management.api.model.responseModel.MarkResponseDto;
import com.school.management.api.repository.ExamRepository;
import com.school.management.api.repository.MarkRepository;
import com.school.management.api.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExamRepository examRepository;

    public List<MarkResponseDto> markStudentMarks(List<MarkRequestDto> requestList) {

        if (requestList.isEmpty()) {
            return new ArrayList<>();
        }

        // Collect IDs for bulk validation and query
        List<String> studentIds = requestList.stream().map(MarkRequestDto::getStudentId).distinct().toList();
        List<String> subjectIds = requestList.stream().map(MarkRequestDto::getSubjectId).distinct().toList();
        List<String> examIds = requestList.stream().map(MarkRequestDto::getExamId).distinct().toList();
        List<String> academicYearIds = requestList.stream().map(MarkRequestDto::getAcademicYearId).distinct().toList();

        // 1. Bulk validate subjectId existence
        Set<String> validSubjectIds = subjectRepository.findBySubjectIdIn(subjectIds).stream()
                .map(s -> s.getSubjectId())
                .collect(Collectors.toSet());
        
        List<String> invalidSubjects = subjectIds.stream().filter(id -> !validSubjectIds.contains(id)).toList();
        if (!invalidSubjects.isEmpty()) {
            throw new RuntimeException("The following Subject IDs do not exist: " + invalidSubjects);
        }

        // 2. Bulk validate examId existence
        Set<String> validExamIds = examRepository.findByExamIdIn(examIds).stream()
                .map(e -> e.getExamId())
                .collect(Collectors.toSet());
        
        List<String> invalidExams = examIds.stream().filter(id -> !validExamIds.contains(id)).toList();
        if (!invalidExams.isEmpty()) {
            throw new RuntimeException("The following Exam IDs do not exist: " + invalidExams);
        }

        // 3. Fetch existing records in ONE query
        List<Mark> existingMarks = markRepository.findByStudentIdInAndSubjectIdInAndExamIdInAndAcademicYearIdIn(
                studentIds, subjectIds, examIds, academicYearIds
        );

        // Create a set of unique keys for efficient duplicate check
        Set<String> existingKeys = existingMarks.stream()
                .map(m -> generateKey(m.getStudentId(), m.getSubjectId(), m.getExamId(), m.getAcademicYearId()))
                .collect(Collectors.toSet());

        List<Mark> toSave = new ArrayList<>();
        Set<String> processedInRequest = new ArrayList<String>().stream().collect(Collectors.toSet()); // Empty set to start

        for (MarkRequestDto request : requestList) {
            // Validation: Marks range
            if (request.getMarks() < 0 || request.getMarks() > request.getMaxMarks()) {
                throw new RuntimeException("Invalid marks for student " + request.getStudentId() + 
                        ". Marks must be between 0 and " + request.getMaxMarks());
            }

            // Duplicate Check: Database + Request context
            String key = generateKey(request.getStudentId(), request.getSubjectId(), request.getExamId(), request.getAcademicYearId());
            
            if (existingKeys.contains(key)) {
                throw new RuntimeException("Marks already exist in database for Student ID: " + request.getStudentId() + 
                        ", Subject ID: " + request.getSubjectId() + 
                        ", Exam ID: " + request.getExamId());
            }
            
            if (processedInRequest.contains(key)) {
                throw new RuntimeException("Duplicate entry detected within request for Student ID: " + request.getStudentId() + 
                        ", Subject ID: " + request.getSubjectId() + 
                        ", Exam ID: " + request.getExamId());
            }

            Mark mark = new Mark();
            mark.setStudentId(request.getStudentId());
            mark.setClassId(request.getClassId());
            mark.setSubjectId(request.getSubjectId());
            mark.setExamId(request.getExamId());
            mark.setAcademicYearId(request.getAcademicYearId());
            mark.setMarks(request.getMarks());
            mark.setMaxMarks(request.getMaxMarks());
            mark.setGrade(calculateGrade(request.getMarks(), request.getMaxMarks()));
            mark.setRemarks(request.getRemarks());
            mark.setCreatedAt(LocalDateTime.now());
            mark.setUpdatedAt(LocalDateTime.now());

            toSave.add(mark);
            processedInRequest.add(key);
        }

        List<Mark> savedMarks = markRepository.saveAll(toSave);

        return savedMarks.stream().map(this::mapToResponseDto).toList();
    }

    private String generateKey(String studentId, String subjectId, String examId, String academicYearId) {
        return studentId + "|" + subjectId + "|" + examId + "|" + academicYearId;
    }

    private String calculateGrade(Integer marks, Integer maxMarks) {
        if (maxMarks == 0) return "F";
        double percentage = (marks * 100.0) / maxMarks;

        if (percentage >= 90) return "A+";
        if (percentage >= 75) return "A";
        if (percentage >= 60) return "B";
        if (percentage >= 50) return "C";
        return "F";
    }

    private MarkResponseDto mapToResponseDto(Mark mark) {
        MarkResponseDto dto = new MarkResponseDto();
        dto.setId(mark.getId());
        dto.setStudentId(mark.getStudentId());
        dto.setClassId(mark.getClassId());
        dto.setSubjectId(mark.getSubjectId());
        dto.setExamId(mark.getExamId());
        dto.setAcademicYearId(mark.getAcademicYearId());
        dto.setMarks(mark.getMarks());
        dto.setMaxMarks(mark.getMaxMarks());
        dto.setGrade(mark.getGrade());
        dto.setRemarks(mark.getRemarks());
        dto.setCreatedAt(mark.getCreatedAt());
        dto.setUpdatedAt(mark.getUpdatedAt());
        return dto;
    }
}
