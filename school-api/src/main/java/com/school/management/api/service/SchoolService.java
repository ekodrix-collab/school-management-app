package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.School;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.exception.ResourceNotFoundException;
import com.school.management.api.model.requstModel.SchoolRequestDto;
import com.school.management.api.model.responseModel.AddressResponse;
import com.school.management.api.model.responseModel.SchoolResponse;
import com.school.management.api.repository.SchoolRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class SchoolService {

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    MapperService mapperService;

    @Autowired
    AddressService addressService;

    @Transactional
    public SchoolResponse createSchool(SchoolRequestDto request) {
        String role = AuthUtil.getCurrentRole();
        if(!Constants.ROLE_SUPER_ADMIN.equalsIgnoreCase(role)){
            throw new BadRequestException("Super admin can can only create school");
        }

        if (schoolRepository.existsBySchoolId(request.getSchoolId())) {
            throw new ResourceNotFoundException("School code already exists");
        }

        request.getAddress().setSchoolId(request.getSchoolId());
        AddressResponse address = addressService.createAddress(request.getAddress());

        School school = new School();
        school.setSchoolId(request.getSchoolId());
        school.setEmail(request.getEmail());
        school.setPhone(request.getPhone());
        school.setAddressId(address.getAddressId());
        school.setIsActive(true);
        school.setSchoolName(address.getName());
        school.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        school.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));


        School savedSchool = schoolRepository.save(school);
        return mapperService.toSchoolResponse(savedSchool,address);

    }


}
