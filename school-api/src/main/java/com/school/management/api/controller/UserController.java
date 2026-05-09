package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.UserRequestDto;
import com.school.management.api.model.responseModel.UserResponse;
import com.school.management.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = Constants.USER_ROUTE)
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public UserResponse createUser(@RequestBody UserRequestDto request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable UUID userId){
        return userService.getUserById(userId);
    }

    //get
    //edit
    //delete


}
