package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.User;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.StudentRequestDto;
import com.school.management.api.model.responseModel.StudentResponseDto;
import com.school.management.api.model.responseModel.UserResponse;
import com.school.management.api.repository.UserRepository;
import com.school.management.api.service.authService.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdmissionService {

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @Autowired
    ParentService parentService;

    @Autowired
    UserRepository userRepository;

    public StudentResponseDto createAdmission(StudentRequestDto request) {
        if(request.getUserDetails() == null){
            throw new BadRequestException("User Details not Found");
        }

        if(request.getParentDetails() == null){
            throw new BadRequestException("Parent Details not Found");
        }

        UUID userId = AuthUtil.getCurrentUserId();
        if(userId == null){
            throw new BadRequestException("Admin can only create admission");
        }
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        request.getUserDetails().setRole(Constants.ROLE_PARENT);
        request.getUserDetails().setSchoolId(user.getSchoolId());
        UserResponse savedUser = userService.createUser(request.getUserDetails());
        request.getParentDetails().setName(savedUser.getName());
        request.getParentDetails().setEmail(savedUser.getMail());
        request.getParentDetails().setMobile(savedUser.getNumber());
        request.getParentDetails().setParentId(savedUser.getUserId());

        parentService.createParent(request.getParentDetails());

        return studentService.createStudent(request);


    }
}
