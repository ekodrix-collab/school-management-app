package com.school.management.api.service;

import com.school.management.api.entity.ClassTimetable;
import com.school.management.api.model.requstModel.ClassTimetableRequest;
import com.school.management.api.model.responseModel.ClassTimetableResponse;
import com.school.management.api.repository.ClassTimetableRepository;

import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassTimeTableService {

    @Autowired
    ClassTimetableRepository classTimetableRepository;


    public ClassTimetableResponse createTimetable(ClassTimetableRequest request) {

        String schoolId = AuthUtil.getCurrentSchoolId();

        boolean exists = classTimetableRepository.existsBySchoolIdAndAcademicYearIdAndClassIdAndDayNameAndPeriodNumber(schoolId, request.getAcademicYearId(), request.getClassId(), request.getDayName(), request.getPeriodNumber());

        if (exists) {
            throw new RuntimeException("Period already assigned");
        }

        ClassTimetable timetable = new ClassTimetable();

        timetable.setTimetableId(IdGenerator.generateStudentId("TT"));
        timetable.setSchoolId(schoolId);
        timetable.setAcademicYearId(request.getAcademicYearId());
        timetable.setClassId(request.getClassId());
        timetable.setClassSubjectId(request.getClassSubjectId());
        timetable.setTeacherId(request.getTeacherId());
        timetable.setDayName(request.getDayName());
        timetable.setPeriodNumber(request.getPeriodNumber());
        timetable.setStartTime(request.getStartTime());
        timetable.setEndTime(request.getEndTime());
        timetable.setCreatedAt(LocalDateTime.now());
        timetable.setUpdatedAt(LocalDateTime.now());

        classTimetableRepository.save(timetable);
        return ClassTimetableResponse.builder().timetableId(timetable.getTimetableId()).message("Timetable created successfully").build();

    }

    public List<ClassTimetableResponse> getClassTimetable(String schoolId, String academicYearId, String classId, String dayName) {

        List<ClassTimetable> timetableList = classTimetableRepository.findBySchoolIdAndAcademicYearIdAndClassIdAndDayNameOrderByPeriodNumberAsc(
                schoolId, academicYearId, classId, dayName);

        return timetableList.stream().map(timetable ->
                ClassTimetableResponse.builder().timetableId(timetable.getTimetableId()).message("Timetable fetched successfully")
                        .build()).collect(Collectors.toList());
    }


}
