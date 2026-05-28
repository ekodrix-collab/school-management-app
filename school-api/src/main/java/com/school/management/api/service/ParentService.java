package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.Parent;
import com.school.management.api.model.requstModel.ParentRequestDto;
import com.school.management.api.model.responseModel.ParentResponse;
import com.school.management.api.repository.ParentRepository;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ParentService {

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MapperService mapperService;


    @Transactional
    public ParentResponse createParent(ParentRequestDto request) {

        Parent parent = null;
        if(request.getMobile() != null){
            parent = parentRepository.findByMobile(request.getMobile()).orElse(null);
        }

        if (parent == null) {
            parent = new Parent();

            parent.setPassword(passwordEncoder.encode(Constants.DUMMY_PASSWORD));
            parent.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
            parent.setIsFirstLogin(true);
            parent.setParentId(MapperService.generateUserId());
            parent.setSchoolId(request.getSchoolId());
            parent.setAddressId(request.getAddressId());
        }

        parent.setMobile(request.getMobile());
        parent.setName(request.getName());
        parent.setAlternateMobile(request.getAlternateMobile());
        parent.setIsActive(true);
        parent.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        Parent saved = parentRepository.save(parent);

        return mapperService.toParentResponse(saved);
    }

    public java.util.List<ParentResponse> getAllParents() {
        String schoolId = com.school.management.api.service.authService.AuthUtil.getCurrentSchoolId();
        java.util.List<Parent> parents = parentRepository.findBySchoolId(schoolId);
        return parents.stream()
                .map(mapperService::toParentResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    public ParentResponse getParentById(String parentId) {
        String schoolId = com.school.management.api.service.authService.AuthUtil.getCurrentSchoolId();
        Parent parent = parentRepository.findByParentId(java.util.UUID.fromString(parentId))
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        if (!parent.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Unauthorized to view this parent");
        }

        return mapperService.toParentResponse(parent);
    }

    @Transactional
    public ParentResponse updateParent(String parentId, ParentRequestDto request) {
        String schoolId = com.school.management.api.service.authService.AuthUtil.getCurrentSchoolId();
        Parent parent = parentRepository.findByParentId(java.util.UUID.fromString(parentId))
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        if (!parent.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Unauthorized to update this parent");
        }

        parent.setMobile(request.getMobile());
        parent.setName(request.getName());
        parent.setAlternateMobile(request.getAlternateMobile());
        parent.setAddressId(request.getAddressId());
        parent.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        Parent saved = parentRepository.save(parent);
        return mapperService.toParentResponse(saved);
    }

    @Transactional
    public void deleteParent(String parentId) {
        String schoolId = com.school.management.api.service.authService.AuthUtil.getCurrentSchoolId();
        Parent parent = parentRepository.findByParentId(java.util.UUID.fromString(parentId))
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        if (!parent.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Unauthorized to delete this parent");
        }

        parentRepository.delete(parent);
    }

}
