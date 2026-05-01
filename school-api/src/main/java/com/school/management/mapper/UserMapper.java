package com.school.management.mapper;

import com.school.management.dto.UserDto;
import com.school.management.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDto userDto);
    
    List<UserDto> toDtoList(List<User> users);
    
    @Mapping(target = "password", ignore = true)
    void updateEntityFromDto(UserDto userDto, @MappingTarget User user);
}
