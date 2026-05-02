package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.Parent;
import com.school.management.api.model.requstModel.ParentRequestDto;
import com.school.management.api.model.responseModel.ParentResponse;
import com.school.management.api.model.responseModel.UserResponse;
import com.school.management.api.repository.ParentRepository;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

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

    @Autowired
    ObjectMapper objectMapper;


    @Transactional
    public ParentResponse createParent(ParentRequestDto request) {

        Parent parent = new Parent();
        parent.setName(request.getName());
        parent.setSpouseName(request.getSpouseName());
        parent.setMobile(request.getMobile());
        parent.setAlternateMobile(request.getAlternateMobile());
        parent.setEmail(request.getEmail());
        parent.setHouseName(request.getHouseName());
        parent.setPlace(request.getPlace());
        parent.setRole(Constants.ROLE_PARENT);
        parent.setCity(request.getCity());
        parent.setDistrict(request.getDistrict());
        parent.setState(request.getState());
        parent.setPincode(request.getPincode());
        parent.setLandmark(request.getLandmark());
        parent.setIsFirstLogin(true);
        parent.setIsActive(true);
        try {
            if (request.getStudentIds() != null && !request.getStudentIds().isEmpty()) {
                String studentIds = objectMapper.writeValueAsString(request.getStudentIds());
                parent.setStudentIds(studentIds);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error converting studentIds", e);
        }
        parent.setPassword(passwordEncoder.encode(Constants.DUMMY_PASSWORD));
        parent.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        parent.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        Parent saved = parentRepository.save(parent);

        return mapperService.toParentResponse(saved);
    }

}
