package com.school.management.service;

import com.school.management.dto.SchoolDto;
import java.util.List;

public interface SchoolService {
    SchoolDto createSchool(SchoolDto schoolDto);
    SchoolDto updateSchool(Long id, SchoolDto schoolDto);
    SchoolDto getSchoolById(Long id);
    List<SchoolDto> getAllSchools();
    void activateDeactivateSchool(Long id, boolean active);
}
