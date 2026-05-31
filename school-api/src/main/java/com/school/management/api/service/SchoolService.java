package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.School;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.exception.ResourceNotFoundException;
import com.school.management.api.model.requstModel.SchoolRequestDto;
import com.school.management.api.model.responseModel.AddressResponse;
import com.school.management.api.model.responseModel.SchoolResponse;
import com.school.management.api.model.responseModel.SchoolTotalCount;
import com.school.management.api.repository.SchoolRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

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

    public java.util.List<SchoolResponse> getAllSchools() {
        return schoolRepository.findAll().stream().map(school -> {
            AddressResponse address = addressService.getAddressById(school.getAddressId());
            return mapperService.toSchoolResponse(school, address);
        }).toList();
    }

    public SchoolResponse getSchoolById(String schoolId) {
        School school = schoolRepository.findBySchoolId(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));
        AddressResponse address = addressService.getAddressById(school.getAddressId());
        return mapperService.toSchoolResponse(school, address);
    }

    @Transactional
    public SchoolResponse updateSchool(String schoolId, SchoolRequestDto request) {
        String role = AuthUtil.getCurrentRole();
        if(!Constants.ROLE_SUPER_ADMIN.equalsIgnoreCase(role)){
            throw new BadRequestException("Super admin can only update school");
        }

        School school = schoolRepository.findBySchoolId(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        if (request.getEmail() != null) school.setEmail(request.getEmail());
        if (request.getPhone() != null) school.setPhone(request.getPhone());
        if (request.getAddress() != null && request.getAddress().getName() != null) {
            school.setSchoolName(request.getAddress().getName());
        }
        school.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        School savedSchool = schoolRepository.save(school);
        AddressResponse address = addressService.getAddressById(savedSchool.getAddressId());
        return mapperService.toSchoolResponse(savedSchool, address);
    }

    @Transactional
    public void deleteSchool(String schoolId) {
        String role = AuthUtil.getCurrentRole();
        if(!Constants.ROLE_SUPER_ADMIN.equalsIgnoreCase(role)){
            throw new BadRequestException("Super admin can only delete school");
        }

        School school = schoolRepository.findBySchoolId(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        addressService.deleteAddress(school.getAddressId());
        schoolRepository.delete(school);
    }

}
