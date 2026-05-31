package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.responseModel.TotalCountResponse;
import com.school.management.api.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.SUPER_ADMIN_ROUTE)
public class SuperAdminController {

    @Autowired
    SuperAdminService superAdminService;

    @GetMapping("/dashboard")
    public TotalCountResponse getTotalCount(){
       return superAdminService.getTotalCount();
    }

}
