package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.User;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.UserRequestDto;
import com.school.management.api.model.responseModel.UserResponse;
import com.school.management.api.repository.UserRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

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
        // String role = request.getRole() != null ? request.getRole() : AuthUtil.getCurrentRole();
        String schoolId = request.getSchoolId() != null ? request.getSchoolId() : AuthUtil.getCurrentSchoolId();

        if (request.getMobile() != null) {
            boolean exists = userRepository.findByMobile(request.getMobile()).isPresent();
            if (exists) {
                throw new BadRequestException("Mobile number already exists");
            }
        }

        User user = new User();

        user.setName(request.getName());
        user.setMobile(request.getMobile());
        user.setRole(request.getRole());
        user.setIsFirstLogin(true);
        user.setSchoolId(schoolId);
        user.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        user.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        user.setEmail(request.getEmail());
        user.setUserId(generateUserId());
        user.setPassword(passwordEncoder.encode(Constants.DUMMY_PASSWORD));

        User savedUser = userRepository.save(user);
        return mapperService.toUserResponse(savedUser);

    }

    public UserResponse getUserById(UUID userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() ->
                new BadRequestException("User record not found for teacher"));
        return mapperService.toUserResponse(user);
    }

    public List<UserResponse> getAllUser(){
        String schoolId = AuthUtil.getCurrentSchoolId();
        List<User> users = userRepository.getAllUser(schoolId);
        return users.stream()
                .map(user -> mapperService.toUserResponse(user))
                .toList();
    }

    public UserResponse updateUser(UUID userId,UserRequestDto request){
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        user.setName(request.getName());
        user.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setRole(request.getRole());
        userRepository.save(user);
        return mapperService.toUserResponse(user);

    }

    public String deleteUser(UUID userId){
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new BadRequestException("User not found"));
        user.setIsActive(false);
        userRepository.save(user);
        return "User deleted successfully";
    }
}
