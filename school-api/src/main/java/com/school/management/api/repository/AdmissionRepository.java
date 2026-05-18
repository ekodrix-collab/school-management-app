package com.school.management.api.repository;

import com.school.management.api.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission,Long> {

    Optional<Admission> findByAdmissionId(String admissionId);

    List<Admission> findAllBySchoolId(String schoolId);

    List<Admission> findAllByAcademicYearIdAndSchoolId(String academicYearId, String schoolId);

    long countByAcademicYearIdAndSchoolId(String academicYearId, String schoolId);

}
