package ru.hogwarts.school.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import ru.hogwarts.school.auditEntity.AuditableEntity;
//import org.hibernate.envers.Audited;
//import org.hibernate.envers.RelationTargetAuditMode;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Faculty extends AuditableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @Column(name = "name")
    private String name;
    @Column(name = "color")
    private String color;

    @OneToMany(mappedBy = "faculty")
    private List<Student> student;

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
    }


}
