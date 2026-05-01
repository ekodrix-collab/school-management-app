package com.school.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@FilterDef(name = "academicYearFilter", parameters = @ParamDef(name = "yearId", type = Long.class))
@Filter(name = "academicYearFilter", condition = "academic_year_id = :yearId")
public abstract class BaseAcademicEntity extends BaseTenantEntity {

    @Column(name = "academic_year_id", nullable = false)
    private Long academicYearId;

    // Note: academicYearId should be set either from a context (like TenantContext)
    // or passed explicitly during creation.
}
