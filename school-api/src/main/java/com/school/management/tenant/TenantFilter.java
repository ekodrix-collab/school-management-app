package com.school.management.tenant;

import com.school.management.context.AcademicYearContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TenantFilter implements Filter {

    private static final String TENANT_HEADER = "X-School-Id";
    private static final String ACADEMIC_YEAR_HEADER = "X-Academic-Year-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String tenantId = req.getHeader(TENANT_HEADER);
        String academicYearId = req.getHeader(ACADEMIC_YEAR_HEADER);

        try {
            if (tenantId != null && !tenantId.isEmpty()) {
                TenantContext.setCurrentTenant(Long.parseLong(tenantId));
            }
            if (academicYearId != null && !academicYearId.isEmpty()) {
                AcademicYearContext.setCurrentAcademicYear(Long.parseLong(academicYearId));
            }
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
            AcademicYearContext.clear();
        }
    }
}
