package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.Admission;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.AdmissionRequest;
import com.school.management.api.model.responseModel.*;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class AdmissionService {

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @Autowired
    ParentService parentService;

    @Autowired
    MapperService mapperService;

    @Autowired
    SchoolClassService schoolClassService;

    @Autowired
    AcademicYearService academicYearService;

    @Autowired
    AddressService addressService;

    @Transactional
    public AdmissionResponse createAdmission(AdmissionRequest request) {

        String role = AuthUtil.getCurrentRole();
        String schoolId = AuthUtil.getCurrentSchoolId();
        SchoolClassResponse schoolClass = null;
        AcademicYearResponseDto academicYear = null;

        if(!Constants.ROLE_ADMIN.equalsIgnoreCase(role)){
            throw new BadRequestException("Admin can only create Admission");
        }

        if(request.getClassId() != null){
            schoolClass = schoolClassService.getClassById(request.getClassId());
        }

        if(request.getAcademicYearId() != null){
            academicYear = academicYearService.getAcademicYearById(request.getAcademicYearId());
        }

        if (request.getUserDetails() != null) {
            request.getUserDetails().setRole(Constants.ROLE_PARENT);
        }
        UserResponse user = userService.createUser(request.getUserDetails());

        request.getStudentDetails().setParentId(user.getUserId());
        StudentResponseDto newStudent = studentService.createStudent(request.getStudentDetails());

        AddressResponse address = addressService.createAddress(request.getAddressDetails());
        request.getParentDetails().setAddressId(address.getAddressId());
        request.getParentDetails().setSchoolId(schoolId);
        request.getParentDetails().setMobile(request.getUserDetails().getPhone());
        ParentResponse parentDetails = parentService.createParent(request.getParentDetails());

        Admission admission = new Admission();
        admission.setAdmissionId(schoolId + "-" + IdGenerator.generateStudentId("AD"));
        admission.setSchoolId(schoolId);
        admission.setClassId(schoolClass.getClassId());
        admission.setStudentId(newStudent.getStudentId());
        admission.setAcademicYearId(academicYear.getAcademicYearId());
        admission.setStatus(Constants.ACTIVE);
        admission.setRollNumber(request.getRollNumber());
        admission.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        admission.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        return mapperService.createAdmissionResponse(newStudent,schoolClass,academicYear,admission);

    }
}
