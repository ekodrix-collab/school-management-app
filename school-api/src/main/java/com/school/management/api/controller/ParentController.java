package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.ParentRequestDto;
import com.school.management.api.model.responseModel.ParentResponse;
import com.school.management.api.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.PARANT_ROUTE)
public class ParentController {

    @Autowired
    ParentService parentService;

    @PostMapping("/create")
    public ParentResponse createParent(@RequestBody ParentRequestDto request) {
        return parentService.createParent(request);
    }

    
}
