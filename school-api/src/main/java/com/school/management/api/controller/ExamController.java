package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Constants.EXAM_ROUTE)
public class ExamController {

    @Autowired
    private ExamService examService;


    //create
    //edit
    //get all
    //get by id
    //delete

}
