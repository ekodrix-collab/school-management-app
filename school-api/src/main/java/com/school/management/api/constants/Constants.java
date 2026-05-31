package com.school.management.api.constants;

public class Constants {

    //ROLES
    public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    public static final String ROLE_ADMIN = "ROLE_ADMIN" ;
    public static final String ROLE_TEACHER = "ROLE_TEACHER";
    public static final String ROLE_PARENT = "ROLE_PARENT";

    //BASIC ROUTES
    public static final String USER_ROUTE = "/api/v1/user" ;
    public static final String AUTH_ROUTE = "/api/v1/auth" ;
    public static final String STUDENT_ROUTE = "/api/v1/student";
    public static final String TEACHER_ROUTE = "/api/v1/teacher" ;
    public static final String SCHOOL_CLASS_ROUTE = "/api/v1/school-class" ;
    public static final String SCHOOL_ROUTE = "/api/v1/school" ;
    public static final String PARENT_ROUTE = "/api/v1/parents" ;
    public static final String ATTENDANCE = "/api/v1/attendance";
    public static final String MARK_ROUTE = "/api/v1/mark" ;
    public static final String SUBJECT_ROUTE = "/api/v1/subject";
    public static final String EXAM_ROUTE = "/api/v1/exam";
    public static final String ACADEMIC_YEAR_ROUTE = "/api/v1/academic-year";
    public static final String CLASS_SUBJECT_ROUTE = "/api/v1/class-subject";
    public static final String TEACHER_SUBJECT_CLASS_ROUTE = "/api/v1/teacher-class-subject";
    public static final String ADMISSION_ROUTE = "/api/v1/admission" ;
    public static final String ADDRESS_ROUTE = "/api/v1/address";
    public static final String CLASS_TIME_TABLE ="/api/v1/time-table";
    public static final String EXAM_SUBJECT_ROUTE = "/api/v1/exam-subjects";
    public static final String FEE_PAYMENT = "/api/v1/fee-payments";
    public static final String FEE_PAYMENT_STRUCTURE = "/api/v1/fee-structures";
    public static final String STUDENT_FEE = "/api/v1/student-fees";
    public static final String STUDENT_MARK_ROUTE = "/api/v1/student-marks";
    public static final String SUPER_ADMIN_ROUTE = "/api/v1/super-admin";

    //GENDER
    public static final String MALE = "MALE";
    public static final String FEMALE = "FEMALE";
    public static final String OTHER = "OTHER";

    //INDIAN TIME
    public static final String INDIAN_TIME = "Asia/Kolkata" ;

    //DUMMY PASSWORD
    public static final String DUMMY_PASSWORD ="12345678";

    //STATUS
    public static final String ACTIVE = "ACTIVE";            //Currently studying
    public static final String PROMOTED = "PROMOTED";        //Moved to next class
    public static final String FAILED = "FAILED";            //Did not pass
    public static final String TRANSFERRED = "TRANSFERRED";  //Moved to another school
    public static final String LEFT = "LEFT";                //Left school
    public static final String COMPLETED = "COMPLETED";      //Finished final standard
    public static final String DE_ACTIVE = "DE_ACTIVE";

}
