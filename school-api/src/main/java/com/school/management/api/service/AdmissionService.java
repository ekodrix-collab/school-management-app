package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.Admission;
import com.school.management.api.entity.Student;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.AdmissionRequest;
import com.school.management.api.model.responseModel.*;
import com.school.management.api.repository.AdmissionRepository;
import com.school.management.api.repository.StudentRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import com.school.management.api.service.mapper.MapperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class AdmissionService {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

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

    @Autowired
    AdmissionRepository admissionRepository;

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
        request.getParentDetails().setMobile(request.getUserDetails().getMobile());
        request.getParentDetails().setName(user.getName());
        parentService.createParent(request.getParentDetails());

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

        Admission saveAdmission = admissionRepository.save(admission);

        return mapperService.createAdmissionResponse(newStudent,schoolClass,academicYear,saveAdmission);

    }

    public List<AdmissionResponse> getAllAdmissions() {
        String schoolId = AuthUtil.getCurrentSchoolId();
        List<Admission> admissions = admissionRepository.findAllBySchoolId(schoolId);

        return admissions.stream().map(this::mapToAdmissionResponse).toList();
    }

    public AdmissionResponse getAdmissionById(String admissionId) {
        Admission admission = admissionRepository.findByAdmissionId(admissionId)
                .orElseThrow(() -> new BadRequestException("Admission not found with id: " + admissionId));

        return mapToAdmissionResponse(admission);
    }

    @Transactional
    public AdmissionResponse updateAdmission(String admissionId, AdmissionRequest request) {
        String role = AuthUtil.getCurrentRole();
        if(!Constants.ROLE_ADMIN.equalsIgnoreCase(role)){
            throw new BadRequestException("Admin can only edit Admission");
        }

        Admission admission = admissionRepository.findByAdmissionId(admissionId)
                .orElseThrow(() -> new BadRequestException("Admission not found with id: " + admissionId));

        if (request.getClassId() != null) {
            SchoolClassResponse schoolClass = schoolClassService.getClassById(request.getClassId());
            admission.setClassId(schoolClass.getClassId());
        }

        if (request.getAcademicYearId() != null) {
            AcademicYearResponseDto academicYear = academicYearService.getAcademicYearById(request.getAcademicYearId());
            admission.setAcademicYearId(academicYear.getAcademicYearId());
        }

        if (request.getRollNumber() != null) {
            admission.setRollNumber(request.getRollNumber());
        }

        if (request.getStatus() != null) {
            admission.setStatus(request.getStatus());
        }

        admission.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        Admission savedAdmission = admissionRepository.save(admission);

        return mapToAdmissionResponse(savedAdmission);
    }

    @Transactional
    public String deleteAdmission(String admissionId) {
        String role = AuthUtil.getCurrentRole();
        if(!Constants.ROLE_ADMIN.equalsIgnoreCase(role)){
            throw new BadRequestException("Admin can only delete Admission");
        }

        Admission admission = admissionRepository.findByAdmissionId(admissionId)
                .orElseThrow(() -> new BadRequestException("Admission not found with id: " + admissionId));

        admissionRepository.delete(admission);
        return "Admission deleted successfully";
    }

    public long getTotalAdmissionsByAcademicYear(String academicYearId) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        return admissionRepository.countByAcademicYearIdAndSchoolId(academicYearId, schoolId);
    }

    public List<AdmissionResponse> getAdmissionsByAcademicYear(String academicYearId) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        List<Admission> admissions = admissionRepository.findAllByAcademicYearIdAndSchoolId(academicYearId, schoolId);

        return admissions.stream().map(this::mapToAdmissionResponse).toList();
    }

    private AdmissionResponse mapToAdmissionResponse(Admission admission) {
        Student student = studentRepository.findByStudentId(admission.getStudentId()).orElse(null);
        StudentResponseDto studentDto = student != null ? mapperService.toCreateStudent(student) : null;

        SchoolClassResponse schoolClass = null;
        if (admission.getClassId() != null) {
            try {
                schoolClass = schoolClassService.getClassById(admission.getClassId());
            } catch (Exception ignored) {}
        }

        AcademicYearResponseDto academicYear = null;
        if (admission.getAcademicYearId() != null) {
            try {
                academicYear = academicYearService.getAcademicYearById(admission.getAcademicYearId());
            } catch (Exception ignored) {}
        }

        return mapperService.createAdmissionResponse(studentDto, schoolClass, academicYear, admission);
    }
}
