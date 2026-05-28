package com.school.management.api.service;


import com.school.management.api.entity.ExamSubject;
import com.school.management.api.entity.StudentMark;
import com.school.management.api.model.requstModel.StudentMarkRequest;
import com.school.management.api.model.responseModel.StudentMarkResponse;
import com.school.management.api.repository.ExamSubjectRepository;
import com.school.management.api.repository.StudentMarkRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentMarkService {

    private final StudentMarkRepository studentMarkRepository;

    private final ExamSubjectRepository examSubjectRepository;

    public StudentMarkResponse createStudentMark(StudentMarkRequest request) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        Boolean alreadyExists = studentMarkRepository.existsByExamSubjectIdAndStudentIdAndSchoolId(
                                request.getExamSubjectId(),
                                request.getStudentId(),
                                schoolId);

        if (alreadyExists) {
            throw new RuntimeException("Student mark already exists");
        }

        ExamSubject examSubject = examSubjectRepository.findByExamSubjectIdAndSchoolId(request.getExamSubjectId(),schoolId)
                        .orElseThrow(() -> new RuntimeException("Exam subject not found"));

        if (request.getObtainedMark() > examSubject.getMaxMark()) {

            throw new RuntimeException("Obtained mark cannot be greater than max mark");
        }

        Double percentage = (request.getObtainedMark() / examSubject.getMaxMark()) * 100;

        String grade = calculateGrade(percentage);

        String resultStatus = request.getObtainedMark() >= examSubject.getPassMark() ? "PASS" : "FAIL";

        StudentMark studentMark = new StudentMark();

        studentMark.setStudentMarkId(IdGenerator.generateStudentId("MARK"));

        studentMark.setSchoolId(schoolId);
        studentMark.setAcademicYearId(request.getAcademicYearId());
        studentMark.setExamId(request.getExamId());
        studentMark.setExamSubjectId(request.getExamSubjectId());
        studentMark.setClassId(request.getClassId());
        studentMark.setStudentId(request.getStudentId());
        studentMark.setObtainedMark(request.getObtainedMark());
        studentMark.setPercentage(percentage);
        studentMark.setGrade(grade);
        studentMark.setResultStatus(resultStatus);
        studentMark.setAttendanceStatus(request.getAttendanceStatus());
        studentMark.setRemarks(request.getRemarks());
        studentMark.setIsPublished(false);
        studentMark.setCreatedAt(LocalDateTime.now());
        studentMark.setUpdatedAt(LocalDateTime.now());
        StudentMark savedStudentMark = studentMarkRepository.save(studentMark);

        return mapToResponse(savedStudentMark);
    }

    public List<StudentMarkResponse> getMarksByExamSubject(String examSubjectId) {

        List<StudentMark> studentMarks = studentMarkRepository.findByExamSubjectId(examSubjectId);
        return studentMarks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<StudentMarkResponse> getAllStudentMarks() {
        String schoolId = AuthUtil.getCurrentSchoolId();
        List<StudentMark> marks = studentMarkRepository.findBySchoolId(schoolId);
        return marks.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public StudentMarkResponse getStudentMarkById(String studentMarkId) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        StudentMark mark = studentMarkRepository.findByStudentMarkId(studentMarkId)
                .orElseThrow(() -> new RuntimeException("Student mark not found"));

        if (!mark.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Unauthorized to view this student mark");
        }

        return mapToResponse(mark);
    }

    public StudentMarkResponse updateStudentMark(String studentMarkId, StudentMarkRequest request) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        StudentMark studentMark = studentMarkRepository.findByStudentMarkId(studentMarkId)
                .orElseThrow(() -> new RuntimeException("Student mark not found"));

        if (!studentMark.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Unauthorized to update this student mark");
        }

        ExamSubject examSubject = examSubjectRepository.findByExamSubjectIdAndSchoolId(studentMark.getExamSubjectId(), schoolId)
                .orElseThrow(() -> new RuntimeException("Exam subject not found"));

        if (request.getObtainedMark() > examSubject.getMaxMark()) {
            throw new RuntimeException("Obtained mark cannot be greater than max mark");
        }

        Double percentage = (request.getObtainedMark() / examSubject.getMaxMark()) * 100;
        String grade = calculateGrade(percentage);
        String resultStatus = request.getObtainedMark() >= examSubject.getPassMark() ? "PASS" : "FAIL";

        studentMark.setObtainedMark(request.getObtainedMark());
        studentMark.setPercentage(percentage);
        studentMark.setGrade(grade);
        studentMark.setResultStatus(resultStatus);
        studentMark.setAttendanceStatus(request.getAttendanceStatus());
        studentMark.setRemarks(request.getRemarks());
        studentMark.setUpdatedAt(LocalDateTime.now());

        StudentMark savedStudentMark = studentMarkRepository.save(studentMark);
        return mapToResponse(savedStudentMark);
    }

    public void deleteStudentMark(String studentMarkId) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        StudentMark studentMark = studentMarkRepository.findByStudentMarkId(studentMarkId)
                .orElseThrow(() -> new RuntimeException("Student mark not found"));

        if (!studentMark.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Unauthorized to delete this student mark");
        }

        studentMarkRepository.delete(studentMark);
    }

    private String calculateGrade(Double percentage) {

        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 80) {
            return "A";
        } else if (percentage >= 70) {
            return "B+";
        } else if (percentage >= 60) {
            return "B";
        } else if (percentage >= 50) {
            return "C";
        } else if (percentage >= 35) {
            return "D";
        } else {
            return "F";
        }
    }

    private StudentMarkResponse mapToResponse(StudentMark studentMark) {

        StudentMarkResponse response = new StudentMarkResponse();

        response.setStudentMarkId(studentMark.getStudentMarkId());
        response.setSchoolId(studentMark.getSchoolId());
        response.setAcademicYearId(studentMark.getAcademicYearId());
        response.setExamId(studentMark.getExamId());
        response.setExamSubjectId(studentMark.getExamSubjectId());
        response.setClassId(studentMark.getClassId());
        response.setStudentId(studentMark.getStudentId());
        response.setObtainedMark(studentMark.getObtainedMark());
        response.setPercentage(studentMark.getPercentage());
        response.setGrade(studentMark.getGrade());
        response.setResultStatus(studentMark.getResultStatus());
        response.setAttendanceStatus(studentMark.getAttendanceStatus());
        response.setRemarks(studentMark.getRemarks());
        response.setIsPublished(studentMark.getIsPublished());
        response.setCreatedAt(studentMark.getCreatedAt());

        return response;
    }

}