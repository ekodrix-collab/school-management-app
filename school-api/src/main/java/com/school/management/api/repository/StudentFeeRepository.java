package com.school.management.api.repository;

import com.school.management.api.entity.StudentFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentFeeRepository extends JpaRepository<StudentFee, Long> {

    Optional<StudentFee> findByStudentFeeId(String studentFeeId);

    Boolean existsByFeeStructureIdAndStudentIdAndSchoolId(String feeStructureId, String studentId,String schoolId);

    List<StudentFee> findByStudentId(String studentId);

    List<StudentFee> findBySchoolId(String schoolId);

}