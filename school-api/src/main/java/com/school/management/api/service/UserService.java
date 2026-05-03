package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.User;
import com.school.management.api.model.requstModel.UserRequestDto;
import com.school.management.api.model.responseModel.UserResponse;
import com.school.management.api.repository.UserRepository;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.school.management.api.service.mapper.MapperService.generateUserId;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MapperService mapperService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserRequestDto request){

        User user = new User();
        user.setName(request.getName());
        user.setMobile(request.getPhone());
        user.setRole(request.getRole());
        user.setIsFirstLogin(true);
        user.setSchoolId(request.getSchoolId());
        user.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        user.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        user.setEmail(request.getEmail());
        user.setUserId(generateUserId());
        user.setPassword(passwordEncoder.encode(Constants.DUMMY_PASSWORD));

        User savedUser = userRepository.save(user);
        return mapperService.toUserResponse(savedUser);

    }


}
