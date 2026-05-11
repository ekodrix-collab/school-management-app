package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Constants.MARK_ROUTE)
public class MarkController {

    @Autowired
    private MarkService markService;

    //create
    //edit
    //get all
    //get by id
    //delete

}
