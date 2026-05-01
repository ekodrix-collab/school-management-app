package com.school.management.config;

import com.school.management.context.AcademicYearContext;
import com.school.management.tenant.TenantContext;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataIsolationAspect {

    @Autowired
    private EntityManager entityManager;

    @Pointcut("within(org.springframework.data.repository.Repository+)")
    public void repositoryMethods() {}

    @Before("repositoryMethods()")
    public void enableFilters() {
        Session session = entityManager.unwrap(Session.class);
        
        // Enable School ID Filter
        Long tenantId = TenantContext.getCurrentTenant();
        if (tenantId != null) {
            session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
        }

        // Enable Academic Year Filter
        Long academicYearId = AcademicYearContext.getCurrentAcademicYear();
        if (academicYearId != null) {
            session.enableFilter("academicYearFilter").setParameter("yearId", academicYearId);
        }
    }
}
