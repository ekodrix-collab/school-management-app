package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.MarkRequestDto;
import com.school.management.api.model.responseModel.MarkResponseDto;
import com.school.management.api.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.MARK_ROUTE)
public class MarkController {

    @Autowired
    private MarkService markService;

    @PostMapping("/add")
    public List<MarkResponseDto> addMarks(@RequestBody List<MarkRequestDto> requestList) {
        return markService.markStudentMarks(requestList);
    }

}
