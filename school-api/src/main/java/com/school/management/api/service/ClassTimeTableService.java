package com.school.management.api.service;

import com.school.management.api.entity.ClassTimetable;
import com.school.management.api.model.requstModel.ClassTimetableRequest;
import com.school.management.api.model.responseModel.ClassTimetableResponse;
import com.school.management.api.repository.ClassTimetableRepository;

import com.school.management.api.exception.BadRequestException;
import com.school.management.api.exception.ResourceNotFoundException;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return ClassTimetableResponse.builder()
                .timetableId(timetable.getTimetableId())
                .schoolId(timetable.getSchoolId())
                .academicYearId(timetable.getAcademicYearId())
                .classId(timetable.getClassId())
                .classSubjectId(timetable.getClassSubjectId())
                .teacherId(timetable.getTeacherId())
                .dayName(timetable.getDayName())
                .periodNumber(timetable.getPeriodNumber())
                .startTime(timetable.getStartTime())
                .endTime(timetable.getEndTime())
                .isActive(timetable.getIsActive())
                .message("Timetable created successfully")
                .build();

    }

    public List<ClassTimetableResponse> getClassTimetable(String schoolId, String academicYearId, String classId, String dayName) {

        List<ClassTimetable> timetableList = classTimetableRepository.findBySchoolIdAndAcademicYearIdAndClassIdAndDayNameOrderByPeriodNumberAsc(
                schoolId, academicYearId, classId, dayName);

        return timetableList.stream().map(timetable ->
                ClassTimetableResponse.builder()
                        .timetableId(timetable.getTimetableId())
                        .schoolId(timetable.getSchoolId())
                        .academicYearId(timetable.getAcademicYearId())
                        .classId(timetable.getClassId())
                        .classSubjectId(timetable.getClassSubjectId())
                        .teacherId(timetable.getTeacherId())
                        .dayName(timetable.getDayName())
                        .periodNumber(timetable.getPeriodNumber())
                        .startTime(timetable.getStartTime())
                        .endTime(timetable.getEndTime())
                        .isActive(timetable.getIsActive())
                        .message("Timetable fetched successfully")
                        .build()).collect(Collectors.toList());
    }

    public ClassTimetableResponse getTimetableById(String timetableId) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        ClassTimetable timetable = classTimetableRepository.findByTimetableId(timetableId)
                .orElseThrow(() -> new ResourceNotFoundException("Timetable period not found with ID: " + timetableId));

        if (!timetable.getSchoolId().equals(schoolId)) {
            throw new BadRequestException("Timetable period does not belong to this school");
        }

        return ClassTimetableResponse.builder()
                .timetableId(timetable.getTimetableId())
                .schoolId(timetable.getSchoolId())
                .academicYearId(timetable.getAcademicYearId())
                .classId(timetable.getClassId())
                .classSubjectId(timetable.getClassSubjectId())
                .teacherId(timetable.getTeacherId())
                .dayName(timetable.getDayName())
                .periodNumber(timetable.getPeriodNumber())
                .startTime(timetable.getStartTime())
                .endTime(timetable.getEndTime())
                .isActive(timetable.getIsActive())
                .message("Timetable fetched successfully")
                .build();
    }

    @Transactional
    public ClassTimetableResponse updateTimetable(String timetableId, ClassTimetableRequest request) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        ClassTimetable timetable = classTimetableRepository.findByTimetableId(timetableId)
                .orElseThrow(() -> new ResourceNotFoundException("Timetable period not found with ID: " + timetableId));

        if (!timetable.getSchoolId().equals(schoolId)) {
            throw new BadRequestException("Timetable period does not belong to this school");
        }

        boolean isUniqueFieldChanged = false;
        String academicYearId = timetable.getAcademicYearId();
        String classId = timetable.getClassId();
        String dayName = timetable.getDayName();
        Integer periodNumber = timetable.getPeriodNumber();

        if (request.getAcademicYearId() != null && !request.getAcademicYearId().equals(academicYearId)) {
            academicYearId = request.getAcademicYearId();
            isUniqueFieldChanged = true;
        }
        if (request.getClassId() != null && !request.getClassId().equals(classId)) {
            classId = request.getClassId();
            isUniqueFieldChanged = true;
        }
        if (request.getDayName() != null && !request.getDayName().equals(dayName)) {
            dayName = request.getDayName();
            isUniqueFieldChanged = true;
        }
        if (request.getPeriodNumber() != null && !request.getPeriodNumber().equals(periodNumber)) {
            periodNumber = request.getPeriodNumber();
            isUniqueFieldChanged = true;
        }

        if (isUniqueFieldChanged) {
            boolean exists = classTimetableRepository.existsBySchoolIdAndAcademicYearIdAndClassIdAndDayNameAndPeriodNumber(
                    schoolId, academicYearId, classId, dayName, periodNumber
            );
            if (exists) {
                throw new BadRequestException("Period already assigned for the new slot");
            }
        }

        if (request.getAcademicYearId() != null) timetable.setAcademicYearId(request.getAcademicYearId());
        if (request.getClassId() != null) timetable.setClassId(request.getClassId());
        if (request.getClassSubjectId() != null) timetable.setClassSubjectId(request.getClassSubjectId());
        if (request.getTeacherId() != null) timetable.setTeacherId(request.getTeacherId());
        if (request.getDayName() != null) timetable.setDayName(request.getDayName());
        if (request.getPeriodNumber() != null) timetable.setPeriodNumber(request.getPeriodNumber());
        if (request.getStartTime() != null) timetable.setStartTime(request.getStartTime());
        if (request.getEndTime() != null) timetable.setEndTime(request.getEndTime());

        timetable.setUpdatedAt(LocalDateTime.now());

        classTimetableRepository.save(timetable);

        return ClassTimetableResponse.builder()
                .timetableId(timetable.getTimetableId())
                .schoolId(timetable.getSchoolId())
                .academicYearId(timetable.getAcademicYearId())
                .classId(timetable.getClassId())
                .classSubjectId(timetable.getClassSubjectId())
                .teacherId(timetable.getTeacherId())
                .dayName(timetable.getDayName())
                .periodNumber(timetable.getPeriodNumber())
                .startTime(timetable.getStartTime())
                .endTime(timetable.getEndTime())
                .isActive(timetable.getIsActive())
                .message("Timetable updated successfully")
                .build();
    }

    @Transactional
    public ClassTimetableResponse deleteTimetable(String timetableId) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        ClassTimetable timetable = classTimetableRepository.findByTimetableId(timetableId)
                .orElseThrow(() -> new ResourceNotFoundException("Timetable period not found with ID: " + timetableId));

        if (!timetable.getSchoolId().equals(schoolId)) {
            throw new BadRequestException("Timetable period does not belong to this school");
        }

        classTimetableRepository.delete(timetable);

        return ClassTimetableResponse.builder()
                .timetableId(timetableId)
                .message("Timetable period deleted successfully")
                .build();
    }


}
