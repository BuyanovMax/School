package ru.hogwarts.school.auditEntity;

import lombok.Getter;
import lombok.Setter;
import ru.hogwarts.school.listeners.AuditListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditListener.class)
public class AuditableEntity<T extends Serializable> {

    private Instant createdAt;

    private String createdBy;

}
