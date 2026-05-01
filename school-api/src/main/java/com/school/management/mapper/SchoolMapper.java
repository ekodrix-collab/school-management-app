package com.school.management.mapper;

import com.school.management.dto.SchoolDto;
import com.school.management.entity.School;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    School toEntity(SchoolDto schoolDto);
    SchoolDto toDto(School school);
    List<SchoolDto> toDtoList(List<School> schools);
    void updateEntityFromDto(SchoolDto schoolDto, @MappingTarget School school);
}
