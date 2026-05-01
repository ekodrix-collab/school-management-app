package com.school.management.service;

import java.util.List;

public interface PromotionService {
    /**
     * Promotes a list of students from their current class to a new class in a new academic year.
     * 
     * @param studentEnrollmentIds IDs of the current enrollments to promote
     * @param targetClassSectionId ID of the ClassSection in the NEW academic year
     */
    void promoteStudents(List<Long> studentEnrollmentIds, Long targetClassSectionId);
}
