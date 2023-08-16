package ru.hogwarts.school.listeners;

import ru.hogwarts.school.auditEntity.AuditableEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

public class AuditListener {

    @PrePersist
    public void prePersist(AuditableEntity<?> entity) {
        entity.setCreatedAt(Instant.now());
//        setCreatedBy(SecurityContext.getFaculty());
    }

    @PreUpdate
    public void preUpdate(AuditableEntity<?> entity) {
        entity.setCreatedAt(Instant.now());
//        setCreatedBy(SecurityContext.getFaculty());
    }
}
