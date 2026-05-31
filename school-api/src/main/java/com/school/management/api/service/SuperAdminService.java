package com.school.management.api.service;

import com.school.management.api.model.responseModel.*;
import com.school.management.api.repository.SchoolRepository;
import com.school.management.api.repository.StudentRepository;
import com.school.management.api.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SuperAdminService {

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    public TotalCountResponse getTotalCount(){

       Long activeSchoolCount = schoolRepository.getActiveSchoolCount();
       Long totalStudentCount = studentRepository.getStudentCount();
       Long totalTeacherCount = teacherRepository.getTeacherCount();

       Long currentMonthSchoolGrowth = schoolRepository.getCurrentMonthSchoolGrowth();
       Long currentMonthStudentGrowth = studentRepository.getCurrentMonthStudentGrowth();
       Long currentMonthTeacherGrowth = teacherRepository.getCurrentMonthTeacherGrowth();

       TotalCountResponse totalCountResponse = new TotalCountResponse();
       totalCountResponse.setSchoolCount(activeSchoolCount);
       totalCountResponse.setStudentCount(totalStudentCount);
       totalCountResponse.setTeacherCount(totalTeacherCount);
       totalCountResponse.setRevenue(0.0);
       totalCountResponse.setCurrentMonthSchoolGrowth(currentMonthSchoolGrowth);
       totalCountResponse.setCurrentMonthStudentGrowth(currentMonthStudentGrowth);
       totalCountResponse.setCurrentMonthTeacherGrowth(currentMonthTeacherGrowth);
       totalCountResponse.setCurrentMonthRevenue(0.0);

       return totalCountResponse;

    }

}
