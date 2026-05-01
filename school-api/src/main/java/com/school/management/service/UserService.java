package com.school.management.service;

import com.school.management.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto, String password);
    UserDto updateUser(Long id, UserDto userDto);
    UserDto getUserById(Long id);
    UserDto getUserByUsername(String username);
    List<UserDto> getAllUsersBySchool(Long schoolId);
}
