package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.AdmissionRequest;
import com.school.management.api.model.requstModel.UserRequestDto;
import com.school.management.api.model.responseModel.AdmissionResponse;
import com.school.management.api.model.responseModel.UserResponse;
import com.school.management.api.service.AdmissionService;
import com.school.management.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.ADMIN_ROUTE)
public class AdmissionController {

    @Autowired
    AdmissionService admissionService;

    @Autowired
    UserService userService;

    @PostMapping("/admission")
    public AdmissionResponse createAdmission(@RequestBody AdmissionRequest request){
       return admissionService.createAdmission(request);
    }

    @PostMapping("/create")
    public UserResponse createUser(@RequestBody UserRequestDto request) {
        return userService.createUser(request);
    }


    //get
    //edit
    //delete
    //get total admission filter by academic year

}
