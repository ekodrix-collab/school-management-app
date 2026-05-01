package com.school.management.service.impl;

import com.school.management.dto.ClassDto;
import com.school.management.dto.SubjectDto;
import com.school.management.entity.SchoolClass;
import com.school.management.entity.Subject;
import com.school.management.repository.SchoolClassRepository;
import com.school.management.repository.SubjectRepository;
import com.school.management.service.AcademicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcademicServiceImpl implements AcademicService {

    private final SchoolClassRepository classRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional
    public ClassDto createClass(ClassDto classDto) {
        SchoolClass schoolClass = SchoolClass.builder()
                .className(classDto.getClassName())
                .division(classDto.getDivision()) // Note: In new structure, division might be separate
                .build();
        schoolClass = classRepository.save(schoolClass);
        classDto.setId(schoolClass.getId());
        return classDto;
    }

    @Override
    public List<ClassDto> getAllClasses() {
        return classRepository.findAll().stream()
                .map(c -> ClassDto.builder()
                        .id(c.getId())
                        .className(c.getClassName())
                        .division(c.getDivision())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubjectDto createSubject(SubjectDto subjectDto) {
        Subject subject = Subject.builder()
                .name(subjectDto.getName())
                .subjectCode(subjectDto.getSubjectCode())
                .build();
        subject = subjectRepository.save(subject);
        subjectDto.setId(subject.getId());
        return subjectDto;
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(s -> SubjectDto.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .subjectCode(s.getSubjectCode())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void assignSubjectToClass(Long classId, Long subjectId, Long teacherId) {
        // This will now use TeacherAssignment in the new architecture
        // Implementation would link Teacher, Subject, and ClassSection for an Academic Year
    }
}
