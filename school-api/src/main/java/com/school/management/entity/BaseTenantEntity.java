package com.school.management.entity;

import com.school.management.tenant.TenantContext;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseTenantEntity extends AuditableEntity {

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @PrePersist
    public void prePersist() {
        if (this.schoolId == null) {
            this.schoolId = TenantContext.getCurrentTenant();
        }
    }
}
