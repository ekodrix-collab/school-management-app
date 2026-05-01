package com.school.management.context;

public class AcademicYearContext {
    private static final ThreadLocal<Long> CURRENT_ACADEMIC_YEAR = new ThreadLocal<>();

    public static Long getCurrentAcademicYear() {
        return CURRENT_ACADEMIC_YEAR.get();
    }

    public static void setCurrentAcademicYear(Long yearId) {
        CURRENT_ACADEMIC_YEAR.set(yearId);
    }

    public static void clear() {
        CURRENT_ACADEMIC_YEAR.remove();
    }
}
