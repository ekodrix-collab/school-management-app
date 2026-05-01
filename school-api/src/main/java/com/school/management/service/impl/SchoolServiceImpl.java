package com.school.management.service.impl;

import com.school.management.dto.SchoolDto;
import com.school.management.entity.School;
import com.school.management.exception.ResourceNotFoundException;
import com.school.management.mapper.SchoolMapper;
import com.school.management.repository.SchoolRepository;
import com.school.management.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    @Override
    @Transactional
    public SchoolDto createSchool(SchoolDto schoolDto) {
        School school = schoolMapper.toEntity(schoolDto);
        return schoolMapper.toDto(schoolRepository.save(school));
    }

    @Override
    @Transactional
    public SchoolDto updateSchool(Long id, SchoolDto schoolDto) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
        schoolMapper.updateEntityFromDto(schoolDto, school);
        return schoolMapper.toDto(schoolRepository.save(school));
    }

    @Override
    public SchoolDto getSchoolById(Long id) {
        return schoolRepository.findById(id)
                .map(schoolMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
    }

    @Override
    public List<SchoolDto> getAllSchools() {
        return schoolMapper.toDtoList(schoolRepository.findAll());
    }

    @Override
    @Transactional
    public void activateDeactivateSchool(Long id, boolean active) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
        school.setActive(active);
        schoolRepository.save(school);
    }
}
