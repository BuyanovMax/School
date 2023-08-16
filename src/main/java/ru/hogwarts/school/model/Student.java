package ru.hogwarts.school.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hogwarts.school.auditEntity.AuditableEntity;
//import org.hibernate.envers.Audited;
//import org.hibernate.envers.RelationTargetAuditMode;


import javax.persistence.*;
import java.util.Objects;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student extends AuditableEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    public Student( String name, int age, Faculty faculty) {
        this.name = name;
        this.age = age;
        this.faculty = faculty;
    }
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
