package com.school.management.service;

import com.school.management.dto.ClassDto;
import com.school.management.dto.SubjectDto;
import java.util.List;

public interface AcademicService {
    // Class methods
    ClassDto createClass(ClassDto classDto);
    List<ClassDto> getAllClasses();
    
    // Subject methods
    SubjectDto createSubject(SubjectDto subjectDto);
    List<SubjectDto> getAllSubjects();
    
    // Mapping
    void assignSubjectToClass(Long classId, Long subjectId, Long teacherId);
}
