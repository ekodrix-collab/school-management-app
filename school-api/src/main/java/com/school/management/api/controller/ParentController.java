package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.ParentRequestDto;
import com.school.management.api.model.responseModel.ParentResponse;
import com.school.management.api.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(Constants.PARENT_ROUTE)
public class ParentController {

    @Autowired
    ParentService parentService;

    @PostMapping("/admin/create")
    public ParentResponse createParent(@RequestBody ParentRequestDto request) {
        return parentService.createParent(request);
    }

    @GetMapping("/all")
    public List<ParentResponse> getAllParents() {
        return parentService.getAllParents();
    }

    @GetMapping("/{parentId}")
    public ParentResponse getParentById(@PathVariable String parentId) {
        return parentService.getParentById(parentId);
    }

    @PutMapping("/admin/update/{parentId}")
    public ParentResponse updateParent(@PathVariable String parentId, @RequestBody ParentRequestDto request) {
        return parentService.updateParent(parentId, request);
    }

    @DeleteMapping("/admin/delete/{parentId}")
    public void deleteParent(@PathVariable String parentId) {
        parentService.deleteParent(parentId);
    }
}
