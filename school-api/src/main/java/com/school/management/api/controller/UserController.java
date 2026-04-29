package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.USER_ROUTE)
public class UserController {

    @Autowired
    UserService userService;

}
