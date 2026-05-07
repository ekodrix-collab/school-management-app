package com.school.management.api.service;

import com.school.management.api.entity.Subject;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.SubjectRequestDto;
import com.school.management.api.model.responseModel.SubjectResponseDto;
import com.school.management.api.repository.SubjectRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectResponseDto createSubject(SubjectRequestDto requestDto) {

        if (requestDto.getName() == null) {
            throw new BadRequestException("Subject name can not blank");
        }

        String schoolId = AuthUtil.getCurrentSchoolId();
        Subject subject = new Subject();

        subject.setSchoolId(schoolId);
        subject.setSubjectId(IdGenerator.generateStudentId("SUB"));
        subject.setName(requestDto.getName());

        try {
            subjectRepository.save(subject);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Subject already exists in this school");
        }
        return mapToResponseDto(subject);
    }

    public SubjectResponseDto updateSubject(String subjectId, SubjectRequestDto requestDto) {
        Subject subject = subjectRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));

        subject.setName(requestDto.getName());

        Subject updatedSubject = subjectRepository.save(subject);
        return mapToResponseDto(updatedSubject);
    }

    public String deleteSubject(String subjectId) {
        Subject subject = subjectRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));

        subjectRepository.delete(subject);
        return "Subject with ID " + subjectId + " deleted successfully.";
    }

    public List<SubjectResponseDto> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public SubjectResponseDto getSubjectById(String subjectId) {
        Subject subject = subjectRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));
        return mapToResponseDto(subject);
    }

    private SubjectResponseDto mapToResponseDto(Subject subject) {
        SubjectResponseDto dto = new SubjectResponseDto();

        dto.setSubjectId(subject.getSubjectId());
        dto.setName(subject.getName());
        dto.setSchoolId(subject.getSchoolId());
        return dto;
    }
}
