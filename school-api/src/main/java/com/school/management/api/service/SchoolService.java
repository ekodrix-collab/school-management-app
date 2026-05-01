package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.School;
import com.school.management.api.entity.User;
import com.school.management.api.exception.ResourceNotFoundException;
import com.school.management.api.model.requstModel.SchoolRequestDto;
import com.school.management.api.model.responseModel.SchoolResponse;
import com.school.management.api.repository.SchoolRepository;
import com.school.management.api.repository.UserRepository;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static com.school.management.api.service.mapper.MapperService.generateUserId;

@Service
public class SchoolService {

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MapperService mapperService;

    @Transactional
    public SchoolResponse createSchool(SchoolRequestDto request) {

        if (schoolRepository.existsBySchoolCode(request.getSchoolCode())) {
            throw new ResourceNotFoundException("School code already exists");
        }

        School school = new School();
        school.setSchoolName(request.getSchoolName());
        school.setSchoolCode(request.getSchoolCode());
        school.setEmail(request.getEmail());
        school.setPhone(request.getPhone());
        school.setAddress(request.getAddress());
        school.setCity(request.getCity());
        school.setState(request.getState());
        school.setCountry(request.getCountry());
        school.setPincode(request.getPincode());
        school.setLogoUrl(request.getLogoUrl());
        school.setIsActive(true);
        school.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        school.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        School savedSchool = schoolRepository.save(school);

        UUID userId = generateUserId();

        User user = new User();
        user.setName(request.getAdmin().getName());
        user.setMobile(request.getAdmin().getPhone());
        user.setPassword(passwordEncoder.encode(Constants.DUMMY_PASSWORD));
        user.setEmail(request.getAdmin().getEmail());
        user.setRole(Constants.ROLE_ADMIN);
        user.setIsFirstLogin(true);
        user.setUpdatedAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setUserId(userId);

        User savedUser = userRepository.save(user);

        return mapperService.toSchoolResponse(savedSchool,savedUser);
    }


}
